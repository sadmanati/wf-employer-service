package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;
import com.americatech.wfemployerservice.repository.JobOrderHistoryRepository;
import com.americatech.wfemployerservice.repository.JobOrderRepository;
import com.americatech.wfemployerservice.service.JobOrderHistoryCommandService;
import com.americatech.wfemployerservice.service.JobOrderHistoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class JobOrderHistoryCommandServiceImpl implements JobOrderHistoryCommandService {

    private static final Set<String> ALLOWED_STATUS = Set.of(
            "draft", "submitted", "under_validation", "validated",
            "open_for_sourcing", "filled", "deployed", "closed"
    );

    private final JobOrderHistoryRepository repository;
    private final JobOrderRepository jobOrderRepository;
    private final JobOrderHistoryQueryService queryService;

    public JobOrderHistoryCommandServiceImpl(JobOrderHistoryRepository repository,
                                             JobOrderRepository jobOrderRepository,
                                             JobOrderHistoryQueryService queryService) {
        this.repository = repository;
        this.jobOrderRepository = jobOrderRepository;
        this.queryService = queryService;
    }

    @Override
    public JobOrderHistoryEntity create(JobOrderHistoryEntity history) {
        history.setId(null);
        attachJobOrder(history);
        validate(history);
        return repository.save(history);
    }

    @Override
    public JobOrderHistoryEntity update(UUID id, JobOrderHistoryEntity input) {
        JobOrderHistoryEntity existing = queryService.getById(id);

        if (input.getJobOrder() != null && input.getJobOrder().getId() != null) {
            existing.setJobOrder(findJobOrder(input.getJobOrder().getId()));
        }
        existing.setPreviousStatus(input.getPreviousStatus());
        existing.setNewStatus(input.getNewStatus());
        existing.setChangedBy(input.getChangedBy());
        existing.setReasonCode(input.getReasonCode());
        existing.setNotes(input.getNotes());

        validate(existing);
        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Job order history not found: " + id);
        }
        repository.deleteById(id);
    }

    private void attachJobOrder(JobOrderHistoryEntity history) {
        if (history.getJobOrder() == null || history.getJobOrder().getId() == null) {
            throw new IllegalArgumentException("jobOrder.id is required");
        }
        history.setJobOrder(findJobOrder(history.getJobOrder().getId()));
    }

    private JobOrderEntity findJobOrder(UUID jobOrderId) {
        return jobOrderRepository.findById(jobOrderId)
                .orElseThrow(() -> new EntityNotFoundException("Job order not found: " + jobOrderId));
    }

    private void validate(JobOrderHistoryEntity h) {
        if (h.getJobOrder() == null) {
            throw new IllegalArgumentException("jobOrder is required");
        }
        if (h.getChangedBy() == null) {
            throw new IllegalArgumentException("changedBy is required");
        }
        if (h.getNewStatus() == null || h.getNewStatus().isBlank()) {
            throw new IllegalArgumentException("newStatus is required");
        }
        if (!ALLOWED_STATUS.contains(h.getNewStatus())) {
            throw new IllegalArgumentException("Invalid newStatus: " + h.getNewStatus());
        }
        if (h.getPreviousStatus() != null && !ALLOWED_STATUS.contains(h.getPreviousStatus())) {
            throw new IllegalArgumentException("Invalid previousStatus: " + h.getPreviousStatus());
        }
    }
}
