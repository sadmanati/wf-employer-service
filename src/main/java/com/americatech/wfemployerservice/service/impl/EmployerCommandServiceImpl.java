package com.americatech.wfemployerservice.service.impl;

import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.repository.EmployerRepository;
import com.americatech.wfemployerservice.service.EmployerCommandService;
import com.americatech.wfemployerservice.service.EmployerQueryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class EmployerCommandServiceImpl implements EmployerCommandService {

    private final EmployerRepository employerRepository;
    private final EmployerQueryService employerQueryService;

    public EmployerCommandServiceImpl(EmployerRepository employerRepository,
                                      EmployerQueryService employerQueryService) {
        this.employerRepository = employerRepository;
        this.employerQueryService = employerQueryService;
    }

    @Override
    public EmployerEntity create(EmployerEntity employer) {
        employer.setId(null); // ensure new entity
        return employerRepository.save(employer);
    }

    @Override
    public EmployerEntity update(UUID id, EmployerEntity employer) {
        EmployerEntity existing = employerQueryService.getById(id);
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
        return employerRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!employerRepository.existsById(id)) {
            throw new EntityNotFoundException("Employer not found: " + id);
        }
        employerRepository.deleteById(id);
    }
}
