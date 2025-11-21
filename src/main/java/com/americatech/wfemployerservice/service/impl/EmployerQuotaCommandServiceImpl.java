package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;
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
    private final EmployerQuotaEntityMapper entityMapper;

    @Override
    public EmployerQuotaModel create(EmployerQuotaModel quota) {
        EmployerQuotaEntity entity = entityMapper.domainModelToEntity(quota);
        entity = quotaRepository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public EmployerQuotaModel update(UUID id, EmployerQuotaModel input) {
        EmployerQuotaEntity existing = quotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel quota not found: " + id));
        EmployerQuotaEntity entity = entityMapper.domainModelToEntity(input);
        entity.setId(existing.getId());
        entity = quotaRepository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public void delete(UUID id) {
        quotaRepository.deleteById(id);
    }
}
