package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.mapper.EmployerEntityMapper;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.EmployerCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployerCommandServiceImpl implements EmployerCommandService {

    private final EmployerRepository employerRepository;
    private final EmployerEntityMapper employerEntityMapper;

    @Override
    public EmployerModel create(EmployerModel employer) {
        EmployerEntity entity = employerEntityMapper.domainModelToEntity(employer);
        EmployerEntity saved = employerRepository.save(entity);
        return employerEntityMapper.entityToDomainModel(saved);
    }

    @Override
    public EmployerModel update(UUID id, EmployerModel employer) {
        EmployerEntity existing = employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel not found: " + id));

        EmployerEntity entity = employerEntityMapper.domainModelToEntity(employer);
        entity.setId(existing.getId());
        entity = employerRepository.save(entity);
        return employerEntityMapper.entityToDomainModel(entity);
    }

    @Override
    public void delete(UUID id) {
        employerRepository.deleteById(id);
    }
}
