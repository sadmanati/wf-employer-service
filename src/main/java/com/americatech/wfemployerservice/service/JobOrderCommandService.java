package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.JobOrderEntity;

import java.util.UUID;

public interface JobOrderCommandService {
    JobOrderEntity create(JobOrderEntity order);
    JobOrderEntity update(UUID id, JobOrderEntity order);
    void delete(UUID id);
}
