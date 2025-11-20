package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.DemandLetterEntity;

import java.util.UUID;

public interface DemandLetterCommandService {
    DemandLetterEntity create(DemandLetterEntity letter);
    DemandLetterEntity update(UUID id, DemandLetterEntity letter);
    void delete(UUID id);
}
