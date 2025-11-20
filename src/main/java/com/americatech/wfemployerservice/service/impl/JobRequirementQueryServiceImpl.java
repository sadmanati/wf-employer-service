package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.JobRequirementEntity;
import com.americatech.wfemployerservice.repository.JobRequirementRepository;
import com.americatech.wfemployerservice.service.JobRequirementQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class JobRequirementQueryServiceImpl implements JobRequirementQueryService {

    private final JobRequirementRepository repository;

    public JobRequirementQueryServiceImpl(JobRequirementRepository repository) {
        this.repository = repository;
    }

    @Override
    public JobRequirementEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job requirement not found: " + id));
    }

    @Override
    public List<JobRequirementEntity> getAll() {
        return repository.findAll();
    }
}
