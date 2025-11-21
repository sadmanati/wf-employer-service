package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;
import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;
import com.americatech.wfemployerservice.mapper.JobOrderHistoryEntityMapper;
import com.americatech.wfemployerservice.repository.JobOrderHistoryRepository;
import com.americatech.wfemployerservice.repository.JobOrderRepository;
import com.americatech.wfemployerservice.service.JobOrderHistoryCommandService;
import com.americatech.wfemployerservice.service.JobOrderHistoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class JobOrderHistoryCommandServiceImpl implements JobOrderHistoryCommandService {
    private final JobOrderHistoryRepository repository;
    private final JobOrderHistoryEntityMapper entityMapper;


    @Override
    public JobOrderHistoryModel create(JobOrderHistoryModel model) {
        JobOrderHistoryEntity entity = entityMapper.domainModelToEntity(model);
        entity = repository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public JobOrderHistoryModel update(UUID id, JobOrderHistoryModel model) {
        JobOrderHistoryEntity existing = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Job order history not found: " + id));

        JobOrderHistoryEntity entity = entityMapper.domainModelToEntity(model);
        entity.setId(existing.getId());
        entity = repository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
