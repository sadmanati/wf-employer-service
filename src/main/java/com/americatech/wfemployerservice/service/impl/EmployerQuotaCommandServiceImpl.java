package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import com.americatech.wfemployerservice.mapper.EmployerQuotaEntityMapper;
import com.americatech.wfemployerservice.repository.EmployerQuotaRepository;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.EmployerQuotaCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployerQuotaCommandServiceImpl implements EmployerQuotaCommandService {

    private final EmployerQuotaRepository quotaRepository;
    private final EmployerRepository employerRepository;
    private final EmployerQuotaEntityMapper entityMapper;

    @Override
    public EmployerQuotaModel create(EmployerQuotaModel quota) {
        EmployerQuotaEntity entity = entityMapper.domainModelToEntity(quota);
        attachEmployer(entity);
        applyDerivedAndValidate(entity);
        EmployerQuotaEntity saved = quotaRepository.save(entity);
        return entityMapper.entityToDomainModel(saved);
    }

    @Override
    public EmployerQuotaModel update(UUID id, EmployerQuotaModel input) {
        EmployerQuotaEntity existing = quotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel quota not found: " + id));

        if (input.getEmployerId() != null) {
            EmployerEntity employer = findEmployer(input.getEmployerId());
            existing.setEmployer(employer);
        }
        existing.setJobCategory(input.getJobCategory());
        existing.setTotalQuota(input.getTotalQuota());
        existing.setUsedQuota(input.getUsedQuota());
        existing.setValidFrom(input.getValidFrom());
        existing.setValidUntil(input.getValidUntil());
        existing.setMohreReference(input.getMohreReference());

        applyDerivedAndValidate(existing);
        EmployerQuotaEntity saved = quotaRepository.save(existing);
        return entityMapper.entityToDomainModel(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!quotaRepository.existsById(id)) {
            throw new EntityNotFoundException("EmployerModel quota not found: " + id);
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
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel not found: " + employerId));
    }

    private void applyDerivedAndValidate(EmployerQuotaEntity quota) {
        if (quota.getUsedQuota() == null) quota.setUsedQuota(0);
        if (quota.getTotalQuota() == null) {
            throw new IllegalArgumentException("totalQuota is required");
        }

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

        int available = quota.getTotalQuota() - quota.getUsedQuota();
        quota.setAvailableQuota(available);
    }
}
