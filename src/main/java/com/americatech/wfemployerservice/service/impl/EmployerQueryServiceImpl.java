package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.mapper.EmployerEntityMapper;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.EmployerQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployerQueryServiceImpl implements EmployerQueryService {

    private final EmployerRepository employerRepository;
    private final EmployerEntityMapper employerEntityMapper;


    @Override
    public EmployerModel getById(UUID id) {
        EmployerEntity entity = employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel not found: " + id));
        return employerEntityMapper.entityToDomainModel(entity);
    }

    @Override
    public List<EmployerModel> getAll() {
        return employerEntityMapper.entityToDomainModel(employerRepository.findAll());
    }
}
