package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.EmployerEntity;
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

    public EmployerQueryServiceImpl(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Override
    public EmployerEntity getById(UUID id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found: " + id));
    }

    @Override
    public List<EmployerEntity> getAll() {
        return employerRepository.findAll();
    }
}
