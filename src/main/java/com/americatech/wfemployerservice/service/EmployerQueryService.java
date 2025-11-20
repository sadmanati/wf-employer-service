package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.Employer;

import java.util.List;
import java.util.UUID;

public interface EmployerQueryService {
    Employer getById(UUID id);
    List<Employer> getAll();
}
