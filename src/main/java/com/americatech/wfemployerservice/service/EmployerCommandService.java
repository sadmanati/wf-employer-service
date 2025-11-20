package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.Employer;

import java.util.UUID;

public interface EmployerCommandService {
    Employer create(Employer employer);
    Employer update(UUID id, Employer employer);
    void delete(UUID id);
}
