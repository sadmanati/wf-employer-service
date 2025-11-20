package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.JobRequirementEntity;

import java.util.List;
import java.util.UUID;

public interface JobRequirementQueryService {
    JobRequirementEntity getById(UUID id);
    List<JobRequirementEntity> getAll();
}
