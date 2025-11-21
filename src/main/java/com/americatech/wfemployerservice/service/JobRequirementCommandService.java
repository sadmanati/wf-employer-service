package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.JobRequirementModel;

import java.util.UUID;

public interface JobRequirementCommandService {
    JobRequirementModel create(JobRequirementModel requirement);
    JobRequirementModel update(UUID id, JobRequirementModel requirement);
    void delete(UUID id);
}
