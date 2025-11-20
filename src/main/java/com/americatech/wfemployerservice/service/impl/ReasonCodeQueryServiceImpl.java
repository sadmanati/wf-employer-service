package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.ReasonCodeEntity;
import com.americatech.wfemployerservice.repository.ReasonCodeRepository;
import com.americatech.wfemployerservice.service.ReasonCodeQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReasonCodeQueryServiceImpl implements ReasonCodeQueryService {

    private final ReasonCodeRepository repository;

    public ReasonCodeQueryServiceImpl(ReasonCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReasonCodeEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reason code not found: " + id));
    }

    @Override
    public List<ReasonCodeEntity> getAll() {
        return repository.findAll();
    }
}
