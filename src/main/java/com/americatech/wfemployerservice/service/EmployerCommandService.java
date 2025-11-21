package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.EmployerModel;

import java.util.UUID;

public interface EmployerCommandService {
    EmployerModel create(EmployerModel employer);
    EmployerModel update(UUID id, EmployerModel employer);
    void delete(UUID id);
}
