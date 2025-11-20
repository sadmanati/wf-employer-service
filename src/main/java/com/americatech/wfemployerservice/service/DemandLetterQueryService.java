package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.DemandLetterEntity;

import java.util.List;
import java.util.UUID;

public interface DemandLetterQueryService {
    DemandLetterEntity getById(UUID id);
    List<DemandLetterEntity> getAll();
}
