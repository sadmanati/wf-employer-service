package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;
import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;
import com.americatech.wfemployerservice.mapper.JobOrderHistoryEntityMapper;
import com.americatech.wfemployerservice.repository.JobOrderHistoryRepository;
import com.americatech.wfemployerservice.service.JobOrderHistoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobOrderHistoryQueryServiceImpl implements JobOrderHistoryQueryService {

    private final JobOrderHistoryRepository repository;
    private final JobOrderHistoryEntityMapper entityMapper;

    @Override
    public JobOrderHistoryModel getById(UUID id) {
        JobOrderHistoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job order history not found: " + id));
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public List<JobOrderHistoryModel> getAll() {
        List<JobOrderHistoryEntity> entities = repository.findAll();
        return entityMapper.entityToDomainModel(entities);
    }
}
