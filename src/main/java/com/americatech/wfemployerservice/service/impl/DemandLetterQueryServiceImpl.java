package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import com.americatech.wfemployerservice.repository.DemandLetterRepository;
import com.americatech.wfemployerservice.service.DemandLetterQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class DemandLetterQueryServiceImpl implements DemandLetterQueryService {

    private final DemandLetterRepository demandLetterRepository;

    public DemandLetterQueryServiceImpl(DemandLetterRepository demandLetterRepository) {
        this.demandLetterRepository = demandLetterRepository;
    }

    @Override
    public DemandLetterEntity getById(UUID id) {
        return demandLetterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demand letter not found: " + id));
    }

    @Override
    public List<DemandLetterEntity> getAll() {
        return demandLetterRepository.findAll();
    }
}
