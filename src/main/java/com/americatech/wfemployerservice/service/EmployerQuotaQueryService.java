package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;

import java.util.List;
import java.util.UUID;

public interface EmployerQuotaQueryService {
    EmployerQuotaModel getById(UUID id);
    List<EmployerQuotaModel> getAll();
}
