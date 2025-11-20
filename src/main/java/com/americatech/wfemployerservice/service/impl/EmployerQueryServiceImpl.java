package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.Employer;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.mapper.EmployerEntityMapper;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.EmployerQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmployerQueryServiceImpl implements EmployerQueryService {

    private final EmployerRepository employerRepository;
    private final EmployerEntityMapper employerEntityMapper;

    public EmployerQueryServiceImpl(EmployerRepository employerRepository,
                                    EmployerEntityMapper employerEntityMapper) {
        this.employerRepository = employerRepository;
        this.employerEntityMapper = employerEntityMapper;
    }

    @Override
    public Employer getById(UUID id) {
        EmployerEntity entity = employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found: " + id));
        return employerEntityMapper.entityToDomainModel(entity);
    }

    @Override
    public List<Employer> getAll() {
        return employerEntityMapper.entityToDomainModel(employerRepository.findAll());
    }
}
