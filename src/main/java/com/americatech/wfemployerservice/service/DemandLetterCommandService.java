package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.domain.DemandLetterModel;

import java.util.UUID;

public interface DemandLetterCommandService {
    DemandLetterModel create(DemandLetterModel letter);
    DemandLetterModel update(UUID id, DemandLetterModel letter);
    void delete(UUID id);
}
