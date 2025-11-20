package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.repository.JobOrderRepository;
import com.americatech.wfemployerservice.service.JobOrderQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class JobOrderQueryServiceImpl implements JobOrderQueryService {

    private final JobOrderRepository jobOrderRepository;

    public JobOrderQueryServiceImpl(JobOrderRepository jobOrderRepository) {
        this.jobOrderRepository = jobOrderRepository;
    }

    @Override
    public JobOrderEntity getById(UUID id) {
        return jobOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job order not found: " + id));
    }

    @Override
    public List<JobOrderEntity> getAll() {
        return jobOrderRepository.findAll();
    }
}
