package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.JobOrderEntity;

import java.util.List;
import java.util.UUID;

public interface JobOrderQueryService {
    JobOrderEntity getById(UUID id);
    List<JobOrderEntity> getAll();
}
