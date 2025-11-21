package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.JobOrderModel;
import com.americatech.wfemployerservice.entity.JobOrderEntity;

import java.util.List;
import java.util.UUID;

public interface JobOrderQueryService {
    JobOrderModel getById(UUID id);
    List<JobOrderModel> getAll();
}
