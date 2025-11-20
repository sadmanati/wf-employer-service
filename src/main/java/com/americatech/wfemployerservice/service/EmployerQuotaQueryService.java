package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;

import java.util.List;
import java.util.UUID;

public interface EmployerQuotaQueryService {
    EmployerQuotaEntity getById(UUID id);
    List<EmployerQuotaEntity> getAll();
}
