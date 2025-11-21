package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.DemandLetterModel;
import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import com.americatech.wfemployerservice.mapper.DemandLetterEntityMapper;
import com.americatech.wfemployerservice.repository.DemandLetterRepository;
import com.americatech.wfemployerservice.service.DemandLetterQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DemandLetterQueryServiceImpl implements DemandLetterQueryService {

    private final DemandLetterRepository demandLetterRepository;
    private final DemandLetterEntityMapper entityMapper;


    @Override
    public DemandLetterModel getById(UUID id) {
        DemandLetterEntity entity = demandLetterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demand letter not found: " + id));
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public List<DemandLetterModel> getAll() {
        return entityMapper.entityToDomainModel(demandLetterRepository.findAll());
    }
}
