package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.ReasonCodeEntity;

import java.util.List;
import java.util.UUID;

public interface ReasonCodeQueryService {
    ReasonCodeEntity getById(UUID id);
    List<ReasonCodeEntity> getAll();
}
