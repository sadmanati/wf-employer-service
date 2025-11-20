package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;

import java.util.UUID;

public interface EmployerQuotaCommandService {
    EmployerQuotaEntity create(EmployerQuotaEntity quota);
    EmployerQuotaEntity update(UUID id, EmployerQuotaEntity quota);
    void delete(UUID id);
}
