package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.EmployerModel;

import java.util.List;
import java.util.UUID;

public interface EmployerQueryService {
    EmployerModel getById(UUID id);
    List<EmployerModel> getAll();
}
