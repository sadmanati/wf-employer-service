package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.repository.JobOrderRepository;
import com.americatech.wfemployerservice.service.JobOrderQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobOrderQueryServiceImpl implements JobOrderQueryService {

    private final JobOrderRepository jobOrderRepository;


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
