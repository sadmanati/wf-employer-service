package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.JobRequirementEntity;

import java.util.UUID;

public interface JobRequirementCommandService {
    JobRequirementEntity create(JobRequirementEntity requirement);
    JobRequirementEntity update(UUID id, JobRequirementEntity requirement);
    void delete(UUID id);
}
