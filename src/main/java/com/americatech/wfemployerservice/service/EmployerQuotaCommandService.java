package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;

import java.util.UUID;

public interface EmployerQuotaCommandService {
    EmployerQuotaModel create(EmployerQuotaModel quota);
    EmployerQuotaModel update(UUID id, EmployerQuotaModel quota);
    void delete(UUID id);
}
