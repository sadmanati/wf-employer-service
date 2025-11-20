package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;

import java.util.List;
import java.util.UUID;

public interface JobOrderHistoryQueryService {
    JobOrderHistoryEntity getById(UUID id);
    List<JobOrderHistoryEntity> getAll();
}
