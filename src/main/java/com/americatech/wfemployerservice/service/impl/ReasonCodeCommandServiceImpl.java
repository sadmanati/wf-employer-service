package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.ReasonCodeEntity;
import com.americatech.wfemployerservice.repository.ReasonCodeRepository;
import com.americatech.wfemployerservice.service.ReasonCodeCommandService;
import com.americatech.wfemployerservice.service.ReasonCodeQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReasonCodeCommandServiceImpl implements ReasonCodeCommandService {

    private static final Set<String> ALLOWED_APPLICABLE_TO = Set.of("employer", "workforce_uae");
    private static final Set<String> ALLOWED_ACTION_TYPES = Set.of("reject", "return", "withdraw");
    private static final Set<String> ALLOWED_CONSEQUENCES = Set.of("rework", "escalation", "hard_stop", "none");

    private final ReasonCodeRepository repository;
    private final ReasonCodeQueryService queryService;


    @Override
    public ReasonCodeEntity create(ReasonCodeEntity rc) {
        applyDefaults(rc);
        validate(rc);
        ensureUniqueCode(rc.getCode(), null);
        return repository.save(rc);
    }

    @Override
    public ReasonCodeEntity update(UUID id, ReasonCodeEntity input) {
        ReasonCodeEntity existing = queryService.getById(id);

        existing.setCode(input.getCode());
        existing.setDescription(input.getDescription());
        existing.setApplicableTo(input.getApplicableTo());
        existing.setActionType(input.getActionType());
        existing.setWorkflowConsequence(input.getWorkflowConsequence());
        existing.setIsActive(input.getIsActive());
        existing.setDisplayOrder(input.getDisplayOrder());

        applyDefaults(existing);
        validate(existing);
        ensureUniqueCode(existing.getCode(), existing.getId());
        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Reason code not found: " + id);
        }
        repository.deleteById(id);
    }

    private void applyDefaults(ReasonCodeEntity rc) {
        if (rc.getIsActive() == null) rc.setIsActive(true);
        if (rc.getDisplayOrder() == null) rc.setDisplayOrder(999);
    }

    private void validate(ReasonCodeEntity rc) {
        if (rc.getCode() == null || rc.getCode().isBlank()) {
            throw new IllegalArgumentException("code is required");
        }
        if (rc.getDescription() == null || rc.getDescription().isBlank()) {
            throw new IllegalArgumentException("description is required");
        }
        if (rc.getApplicableTo() == null || rc.getApplicableTo().isBlank()) {
            throw new IllegalArgumentException("applicableTo is required");
        }
        if (!ALLOWED_APPLICABLE_TO.contains(rc.getApplicableTo())) {
            throw new IllegalArgumentException("Invalid applicableTo: " + rc.getApplicableTo());
        }
        if (rc.getActionType() == null || rc.getActionType().isBlank()) {
            throw new IllegalArgumentException("actionType is required");
        }
        if (!ALLOWED_ACTION_TYPES.contains(rc.getActionType())) {
            throw new IllegalArgumentException("Invalid actionType: " + rc.getActionType());
        }
        if (rc.getWorkflowConsequence() == null || rc.getWorkflowConsequence().isBlank()) {
            throw new IllegalArgumentException("workflowConsequence is required");
        }
        if (!ALLOWED_CONSEQUENCES.contains(rc.getWorkflowConsequence())) {
            throw new IllegalArgumentException("Invalid workflowConsequence: " + rc.getWorkflowConsequence());
        }
        if (rc.getDisplayOrder() != null && rc.getDisplayOrder() < 0) {
            throw new IllegalArgumentException("displayOrder must be >= 0");
        }
    }

    private void ensureUniqueCode(String code, UUID currentId) {
        repository.findByCode(code).ifPresent(existing -> {
            if (currentId == null || !existing.getId().equals(currentId)) {
                throw new IllegalArgumentException("Reason code with code '" + code + "' already exists");
            }
        });
    }
}
