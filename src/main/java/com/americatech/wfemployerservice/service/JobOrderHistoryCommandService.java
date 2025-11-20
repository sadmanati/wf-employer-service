package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;

import java.util.UUID;

public interface JobOrderHistoryCommandService {
    JobOrderHistoryEntity create(JobOrderHistoryEntity history);
    JobOrderHistoryEntity update(UUID id, JobOrderHistoryEntity history);
    void delete(UUID id);
}
