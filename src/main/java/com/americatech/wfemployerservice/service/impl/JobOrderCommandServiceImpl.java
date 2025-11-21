package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.JobOrderModel;
import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.mapper.JobOrderEntityMapper;
import com.americatech.wfemployerservice.repository.DemandLetterRepository;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.repository.JobOrderRepository;
import com.americatech.wfemployerservice.service.JobOrderCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class JobOrderCommandServiceImpl implements JobOrderCommandService {

    private final JobOrderRepository jobOrderRepository;
    private final JobOrderEntityMapper entityMapper;

    @Override
    public JobOrderModel create(JobOrderModel model) {
        JobOrderEntity entity = entityMapper.domainModelToEntity(model);
        entity = jobOrderRepository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public JobOrderModel update(UUID id, JobOrderModel model) {
        JobOrderEntity existing = jobOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job order not found: " + id));
        JobOrderEntity entity = entityMapper.domainModelToEntity(model);
        entity.setId(existing.getId());
        entity = jobOrderRepository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public void delete(UUID id) {
        jobOrderRepository.deleteById(id);
    }
}
