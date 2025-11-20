package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import com.americatech.wfemployerservice.repository.EmployerQuotaRepository;
import com.americatech.wfemployerservice.service.EmployerQuotaQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmployerQuotaQueryServiceImpl implements EmployerQuotaQueryService {

    private final EmployerQuotaRepository quotaRepository;

    public EmployerQuotaQueryServiceImpl(EmployerQuotaRepository quotaRepository) {
        this.quotaRepository = quotaRepository;
    }

    @Override
    public EmployerQuotaEntity getById(UUID id) {
        return quotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employer quota not found: " + id));
    }

    @Override
    public List<EmployerQuotaEntity> getAll() {
        return quotaRepository.findAll();
    }
}
