package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.DemandLetterModel;
import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import com.americatech.wfemployerservice.mapper.DemandLetterEntityMapper;
import com.americatech.wfemployerservice.repository.DemandLetterRepository;
import com.americatech.wfemployerservice.repository.EmployerQuotaRepository;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.DemandLetterCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DemandLetterCommandServiceImpl implements DemandLetterCommandService {

    private static final Set<String> ALLOWED_STATUS = Set.of(
            "draft", "submitted", "under_review", "approved", "rejected", "expired"
    );

    private final DemandLetterRepository demandLetterRepository;
    private final EmployerRepository employerRepository;
    private final EmployerQuotaRepository employerQuotaRepository;
    private final DemandLetterEntityMapper entityMapper;

    @Override
    public DemandLetterModel create(DemandLetterModel letter) {
        DemandLetterEntity entity = entityMapper.domainModelToEntity(letter);
        entity.setId(null);
        attachRelations(entity);
        applyDefaults(entity);
        validate(entity);
        DemandLetterEntity saved = demandLetterRepository.save(entity);
        return entityMapper.entityToDomainModel(saved);
    }

    @Override
    public DemandLetterModel update(UUID id, DemandLetterModel input) {
        DemandLetterEntity existing = demandLetterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demand letter not found: " + id));

        // allow updating associations if provided
        if (input.getEmployerId() != null) {
            EmployerEntity employer = findEmployer(input.getEmployerId());
            existing.setEmployer(employer);
        }
        if (input.getEmployerQuotaId() != null) {
            EmployerQuotaEntity quota = findEmployerQuota(input.getEmployerQuotaId());
            existing.setEmployerQuota(quota);
        }

        existing.setDemandLetterNumber(input.getDemandLetterNumber());
        existing.setJobCategory(input.getJobCategory());
        existing.setRequestedQuantity(input.getRequestedQuantity());
        existing.setSalaryOffered(input.getSalaryOffered());
        existing.setContractDurationMonths(input.getContractDurationMonths());
        existing.setTermsAndConditions(input.getTermsAndConditions());
        existing.setDocumentUrl(input.getDocumentUrl());
        existing.setStatus(input.getStatus());
        existing.setReviewedBy(input.getReviewedBy());
        existing.setReviewedAt(input.getReviewedAt());
        existing.setReviewNotes(input.getReviewNotes());
        existing.setRejectionReasonCode(input.getRejectionReasonCode());

        applyDefaults(existing);
        validate(existing);
        DemandLetterEntity saved = demandLetterRepository.save(existing);
        return entityMapper.entityToDomainModel(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!demandLetterRepository.existsById(id)) {
            throw new EntityNotFoundException("Demand letter not found: " + id);
        }
        demandLetterRepository.deleteById(id);
    }

    private void attachRelations(DemandLetterEntity letter) {
        if (letter.getEmployer() == null || letter.getEmployer().getId() == null) {
            throw new IllegalArgumentException("employer.id is required");
        }
        if (letter.getEmployerQuota() == null || letter.getEmployerQuota().getId() == null) {
            throw new IllegalArgumentException("employerQuota.id is required");
        }
        EmployerEntity employer = findEmployer(letter.getEmployer().getId());
        EmployerQuotaEntity quota = findEmployerQuota(letter.getEmployerQuota().getId());
        letter.setEmployer(employer);
        letter.setEmployerQuota(quota);
    }

    private EmployerEntity findEmployer(UUID employerId) {
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel not found: " + employerId));
    }

    private EmployerQuotaEntity findEmployerQuota(UUID quotaId) {
        return employerQuotaRepository.findById(quotaId)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel quota not found: " + quotaId));
    }

    private void applyDefaults(DemandLetterEntity letter) {
        if (letter.getStatus() == null || letter.getStatus().isBlank()) {
            letter.setStatus("draft");
        }
    }

    private void validate(DemandLetterEntity letter) {
        // required fields
        if (letter.getEmployer() == null) {
            throw new IllegalArgumentException("employer is required");
        }
        if (letter.getEmployerQuota() == null) {
            throw new IllegalArgumentException("employerQuota is required");
        }
        if (letter.getDemandLetterNumber() == null || letter.getDemandLetterNumber().isBlank()) {
            throw new IllegalArgumentException("demandLetterNumber is required");
        }
        if (letter.getJobCategory() == null || letter.getJobCategory().isBlank()) {
            throw new IllegalArgumentException("jobCategory is required");
        }

        // numeric checks (mirror DB constraints)
        if (letter.getRequestedQuantity() == null || letter.getRequestedQuantity() <= 0) {
            throw new IllegalArgumentException("requestedQuantity must be > 0");
        }
        BigDecimal salary = letter.getSalaryOffered();
        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("salaryOffered must be > 0");
        }
        if (letter.getContractDurationMonths() == null ||
                letter.getContractDurationMonths() <= 0 ||
                letter.getContractDurationMonths() > 60) {
            throw new IllegalArgumentException("contractDurationMonths must be between 1 and 60");
        }

        // status check
        if (!ALLOWED_STATUS.contains(letter.getStatus())) {
            throw new IllegalArgumentException("Invalid status: " + letter.getStatus());
        }

        // relation integrity: quota must belong to employer
        EmployerQuotaEntity quota = letter.getEmployerQuota();
        if (quota.getEmployer() == null || quota.getEmployer().getId() == null ||
                letter.getEmployer() == null || letter.getEmployer().getId() == null ||
                !quota.getEmployer().getId().equals(letter.getEmployer().getId())) {
            throw new IllegalArgumentException("EmployerModel quota does not belong to the specified employer");
        }
        // job category match
        if (quota.getJobCategory() != null && letter.getJobCategory() != null &&
                !quota.getJobCategory().equals(letter.getJobCategory())) {
            throw new IllegalArgumentException("jobCategory must match the employer quota jobCategory");
        }
        // optional: ensure requested quantity does not exceed available quota
        if (quota.getAvailableQuota() != null && letter.getRequestedQuantity() != null &&
                letter.getRequestedQuantity() > quota.getAvailableQuota()) {
            throw new IllegalArgumentException("requestedQuantity exceeds available quota");
        }
    }
}
