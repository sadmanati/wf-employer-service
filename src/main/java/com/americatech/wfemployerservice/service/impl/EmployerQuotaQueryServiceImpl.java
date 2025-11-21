package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;
import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import com.americatech.wfemployerservice.mapper.EmployerQuotaEntityMapper;
import com.americatech.wfemployerservice.repository.EmployerQuotaRepository;
import com.americatech.wfemployerservice.service.EmployerQuotaQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployerQuotaQueryServiceImpl implements EmployerQuotaQueryService {

    private final EmployerQuotaRepository quotaRepository;
    private final EmployerQuotaEntityMapper entityMapper;


    @Override
    public EmployerQuotaModel getById(UUID id) {
        EmployerQuotaEntity entity = quotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel quota not found: " + id));
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public List<EmployerQuotaModel> getAll() {
        return entityMapper.entityToDomainModel(quotaRepository.findAll());
    }
}
