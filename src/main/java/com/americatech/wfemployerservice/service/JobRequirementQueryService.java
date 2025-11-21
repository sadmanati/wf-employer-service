package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.JobRequirementModel;

import java.util.List;
import java.util.UUID;

public interface JobRequirementQueryService {
    JobRequirementModel getById(UUID id);
    List<JobRequirementModel> getAll();
}
