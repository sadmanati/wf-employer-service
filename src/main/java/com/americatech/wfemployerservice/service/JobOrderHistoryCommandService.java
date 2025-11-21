package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;

import java.util.UUID;

public interface JobOrderHistoryCommandService {
    JobOrderHistoryModel create(JobOrderHistoryModel model);

    JobOrderHistoryModel update(UUID id, JobOrderHistoryModel model);

    void delete(UUID id);
}
