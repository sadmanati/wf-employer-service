package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.EmployerEntity;

import java.util.UUID;

public interface EmployerCommandService {
    EmployerEntity create(EmployerEntity employer);
    EmployerEntity update(UUID id, EmployerEntity employer);
    void delete(UUID id);
}
