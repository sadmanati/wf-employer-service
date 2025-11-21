package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.DemandLetterModel;
import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import com.americatech.wfemployerservice.mapper.DemandLetterEntityMapper;
import com.americatech.wfemployerservice.repository.DemandLetterRepository;
import com.americatech.wfemployerservice.repository.EmployerQuotaRepository;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.DemandLetterCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DemandLetterCommandServiceImpl implements DemandLetterCommandService {

    private final DemandLetterRepository demandLetterRepository;
    private final EmployerRepository employerRepository;
    private final EmployerQuotaRepository employerQuotaRepository;
    private final DemandLetterEntityMapper entityMapper;

    @Override
    public DemandLetterModel create(DemandLetterModel letter) {
        DemandLetterEntity entity = entityMapper.domainModelToEntity(letter);
        entity = demandLetterRepository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public DemandLetterModel update(UUID id, DemandLetterModel input) {
        DemandLetterEntity existing = demandLetterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demand letter not found: " + id));

        DemandLetterEntity entity = entityMapper.domainModelToEntity(input);
        entity.setId(existing.getId());
        entity = demandLetterRepository.save(entity);
        return entityMapper.entityToDomainModel(entity);
    }

    @Override
    public void delete(UUID id) {
        demandLetterRepository.deleteById(id);
    }
}
