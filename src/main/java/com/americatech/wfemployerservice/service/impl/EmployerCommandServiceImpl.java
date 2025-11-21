package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.mapper.EmployerEntityMapper;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.EmployerCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployerCommandServiceImpl implements EmployerCommandService {

    private final EmployerRepository employerRepository;
    private final EmployerEntityMapper employerEntityMapper;

    @Override
    public EmployerModel create(EmployerModel employer) {
        EmployerEntity entity = employerEntityMapper.domainModelToEntity(employer);
        EmployerEntity saved = employerRepository.save(entity);
        return employerEntityMapper.entityToDomainModel(saved);
    }

    @Override
    public EmployerModel update(UUID id, EmployerModel employer) {
        EmployerEntity existing = employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployerModel not found: " + id));

        // map incoming domain model fields to existing entity
        existing.setUserId(employer.getUserId());
        existing.setCompanyName(employer.getCompanyName());
        existing.setTradeLicenseNumber(employer.getTradeLicenseNumber());
        existing.setTradeLicenseExpiry(employer.getTradeLicenseExpiry());
        existing.setMohreEstablishmentId(employer.getMohreEstablishmentId());
        existing.setTaxRegistrationNumber(employer.getTaxRegistrationNumber());
        existing.setAddress(employer.getAddress());
        existing.setCity(employer.getCity());
        existing.setEmirate(employer.getEmirate());
        existing.setStatus(employer.getStatus());
        existing.setContactDetails(employer.getContactDetails());

        EmployerEntity saved = employerRepository.save(existing);
        return employerEntityMapper.entityToDomainModel(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!employerRepository.existsById(id)) {
            throw new EntityNotFoundException("EmployerModel not found: " + id);
        }
        employerRepository.deleteById(id);
    }
}
