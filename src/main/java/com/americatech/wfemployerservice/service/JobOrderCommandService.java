package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.JobOrderModel;
import com.americatech.wfemployerservice.entity.JobOrderEntity;

import java.util.UUID;

public interface JobOrderCommandService {
    JobOrderModel create(JobOrderModel model);
    JobOrderModel update(UUID id, JobOrderModel model);
    void delete(UUID id);
}
