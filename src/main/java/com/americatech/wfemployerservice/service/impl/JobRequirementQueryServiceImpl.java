package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.entity.JobRequirementEntity;
import com.americatech.wfemployerservice.mapper.JobRequirementEntityMapper;
import com.americatech.wfemployerservice.repository.JobRequirementRepository;
import com.americatech.wfemployerservice.service.JobRequirementQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobRequirementQueryServiceImpl implements JobRequirementQueryService {

    private final JobRequirementRepository repository;
    private final JobRequirementEntityMapper entityMapper;


    @Override
    public JobRequirementModel getById(UUID id) {
        JobRequirementEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job requirement not found: " + id));
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public List<JobRequirementModel> getAll() {
        return entityMapper.entityToDomainModel(repository.findAll());
    }
}
