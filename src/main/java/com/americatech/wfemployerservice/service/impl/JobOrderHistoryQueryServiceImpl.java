package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;
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

    @Override
    public JobOrderHistoryEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job order history not found: " + id));
    }

    @Override
    public List<JobOrderHistoryEntity> getAll() {
        return repository.findAll();
    }
}
