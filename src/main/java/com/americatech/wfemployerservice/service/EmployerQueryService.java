package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.EmployerEntity;

import java.util.List;
import java.util.UUID;

public interface EmployerQueryService {
    EmployerEntity getById(UUID id);
    List<EmployerEntity> getAll();
}
