package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;
import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;

import java.util.List;
import java.util.UUID;

public interface JobOrderHistoryQueryService {
    JobOrderHistoryModel getById(UUID id);
    List<JobOrderHistoryModel> getAll();
}
