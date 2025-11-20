package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import com.americatech.wfemployerservice.repository.EmployerQuotaRepository;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.EmployerQuotaCommandService;
import com.americatech.wfemployerservice.service.EmployerQuotaQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class EmployerQuotaCommandServiceImpl implements EmployerQuotaCommandService {

    private final EmployerQuotaRepository quotaRepository;
    private final EmployerRepository employerRepository;
    private final EmployerQuotaQueryService quotaQueryService;

    public EmployerQuotaCommandServiceImpl(EmployerQuotaRepository quotaRepository,
                                           EmployerRepository employerRepository,
                                           EmployerQuotaQueryService quotaQueryService) {
        this.quotaRepository = quotaRepository;
        this.employerRepository = employerRepository;
        this.quotaQueryService = quotaQueryService;
    }

    @Override
    public EmployerQuotaEntity create(EmployerQuotaEntity quota) {
        quota.setId(null);
        attachEmployer(quota);
        applyDerivedAndValidate(quota);
        return quotaRepository.save(quota);
    }

    @Override
    public EmployerQuotaEntity update(UUID id, EmployerQuotaEntity quota) {
        EmployerQuotaEntity existing = quotaQueryService.getById(id);
        // update fields
        if (quota.getEmployer() != null && quota.getEmployer().getId() != null) {
            EmployerEntity employer = findEmployer(quota.getEmployer().getId());
            existing.setEmployer(employer);
        }
        existing.setJobCategory(quota.getJobCategory());
        existing.setTotalQuota(quota.getTotalQuota());
        existing.setUsedQuota(quota.getUsedQuota());
        existing.setValidFrom(quota.getValidFrom());
        existing.setValidUntil(quota.getValidUntil());
        existing.setMohreReference(quota.getMohreReference());

        applyDerivedAndValidate(existing);
        return quotaRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!quotaRepository.existsById(id)) {
            throw new EntityNotFoundException("Employer quota not found: " + id);
        }
        quotaRepository.deleteById(id);
    }

    private void attachEmployer(EmployerQuotaEntity quota) {
        if (quota.getEmployer() == null || quota.getEmployer().getId() == null) {
            throw new IllegalArgumentException("employer.id is required");
        }
        EmployerEntity employer = findEmployer(quota.getEmployer().getId());
        quota.setEmployer(employer);
    }

    private EmployerEntity findEmployer(UUID employerId) {
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found: " + employerId));
    }

    private void applyDerivedAndValidate(EmployerQuotaEntity quota) {
        // defaults if null to satisfy not-null columns and checks
        if (quota.getUsedQuota() == null) quota.setUsedQuota(0);
        if (quota.getTotalQuota() == null) {
            throw new IllegalArgumentException("totalQuota is required");
        }

        // business validations mirroring DB constraints
        if (quota.getTotalQuota() <= 0) {
            throw new IllegalArgumentException("totalQuota must be > 0");
        }
        if (quota.getUsedQuota() < 0) {
            throw new IllegalArgumentException("usedQuota must be >= 0");
        }
        if (quota.getValidFrom() == null || quota.getValidUntil() == null) {
            throw new IllegalArgumentException("validFrom and validUntil are required");
        }
        if (!quota.getValidUntil().isAfter(quota.getValidFrom())) {
            throw new IllegalArgumentException("validUntil must be after validFrom");
        }

        // derive available = total - used
        int available = quota.getTotalQuota() - quota.getUsedQuota();
        quota.setAvailableQuota(available);
    }
}
