package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.entity.JobRequirementEntity;
import com.americatech.wfemployerservice.mapper.JobRequirementEntityMapper;
import com.americatech.wfemployerservice.repository.JobRequirementRepository;
import com.americatech.wfemployerservice.service.JobRequirementCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class JobRequirementCommandServiceImpl implements JobRequirementCommandService {

    private final JobRequirementRepository repository;
    private final JobRequirementEntityMapper entityMapper;


    @Override
    public JobRequirementModel create(JobRequirementModel requirement) {
        JobRequirementEntity entity = entityMapper.domainModelToEntity(requirement);
        entity = repository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public JobRequirementModel update(UUID id, JobRequirementModel input) {
        JobRequirementEntity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job requirement not found: " + id));

        JobRequirementEntity entity = entityMapper.domainModelToEntity(input);
        entity.setId(existing.getId());
        entity = repository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
