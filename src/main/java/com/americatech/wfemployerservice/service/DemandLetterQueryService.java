package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.DemandLetterModel;

import java.util.List;
import java.util.UUID;

public interface DemandLetterQueryService {
    DemandLetterModel getById(UUID id);
    List<DemandLetterModel> getAll();
}
