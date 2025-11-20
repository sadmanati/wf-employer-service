package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.entity.JobRequirementEntity;
import com.americatech.wfemployerservice.repository.JobOrderRepository;
import com.americatech.wfemployerservice.repository.JobRequirementRepository;
import com.americatech.wfemployerservice.service.JobRequirementCommandService;
import com.americatech.wfemployerservice.service.JobRequirementQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class JobRequirementCommandServiceImpl implements JobRequirementCommandService {

    private static final Set<String> ALLOWED_TYPES = Set.of("mandatory", "preferred");
    private static final Set<String> ALLOWED_CATEGORIES = Set.of(
            "skill", "language", "certification", "education", "experience"
    );

    private final JobRequirementRepository repository;
    private final JobOrderRepository jobOrderRepository;
    private final JobRequirementQueryService queryService;

    public JobRequirementCommandServiceImpl(JobRequirementRepository repository,
                                            JobOrderRepository jobOrderRepository,
                                            JobRequirementQueryService queryService) {
        this.repository = repository;
        this.jobOrderRepository = jobOrderRepository;
        this.queryService = queryService;
    }

    @Override
    public JobRequirementEntity create(JobRequirementEntity requirement) {
        requirement.setId(null);
        attachJobOrder(requirement);
        applyDefaults(requirement);
        validate(requirement);
        return repository.save(requirement);
    }

    @Override
    public JobRequirementEntity update(UUID id, JobRequirementEntity input) {
        JobRequirementEntity existing = queryService.getById(id);

        if (input.getJobOrder() != null && input.getJobOrder().getId() != null) {
            existing.setJobOrder(findJobOrder(input.getJobOrder().getId()));
        }
        existing.setRequirementType(input.getRequirementType());
        existing.setCategory(input.getCategory());
        existing.setName(input.getName());
        existing.setLevel(input.getLevel());
        existing.setMinYearsExperience(input.getMinYearsExperience());
        existing.setWeightage(input.getWeightage());
        existing.setMetadata(input.getMetadata());

        applyDefaults(existing);
        validate(existing);
        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Job requirement not found: " + id);
        }
        repository.deleteById(id);
    }

    private void attachJobOrder(JobRequirementEntity requirement) {
        if (requirement.getJobOrder() == null || requirement.getJobOrder().getId() == null) {
            throw new IllegalArgumentException("jobOrder.id is required");
        }
        requirement.setJobOrder(findJobOrder(requirement.getJobOrder().getId()));
    }

    private JobOrderEntity findJobOrder(UUID jobOrderId) {
        return jobOrderRepository.findById(jobOrderId)
                .orElseThrow(() -> new EntityNotFoundException("Job order not found: " + jobOrderId));
    }

    private void applyDefaults(JobRequirementEntity req) {
        if (req.getMinYearsExperience() == null) {
            req.setMinYearsExperience(0);
        }
    }

    private void validate(JobRequirementEntity req) {
        // Required fields
        if (req.getJobOrder() == null) {
            throw new IllegalArgumentException("jobOrder is required");
        }
        if (req.getRequirementType() == null || req.getRequirementType().isBlank()) {
            throw new IllegalArgumentException("requirementType is required");
        }
        if (!ALLOWED_TYPES.contains(req.getRequirementType())) {
            throw new IllegalArgumentException("Invalid requirementType: " + req.getRequirementType());
        }
        if (req.getCategory() == null || req.getCategory().isBlank()) {
            throw new IllegalArgumentException("category is required");
        }
        if (!ALLOWED_CATEGORIES.contains(req.getCategory())) {
            throw new IllegalArgumentException("Invalid category: " + req.getCategory());
        }
        if (req.getName() == null || req.getName().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }

        // Numeric checks
        if (req.getMinYearsExperience() != null && req.getMinYearsExperience() < 0) {
            throw new IllegalArgumentException("minYearsExperience must be >= 0");
        }
        if (req.getWeightage() != null) {
            BigDecimal w = req.getWeightage();
            if (w.compareTo(BigDecimal.ZERO) < 0 || w.compareTo(BigDecimal.ONE) > 0) {
                throw new IllegalArgumentException("weightage must be between 0 and 1 inclusive");
            }
        }
    }
}
