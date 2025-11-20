package com.americatech.wfemployerservice.service;

import com.americatech.wfemployerservice.entity.ReasonCodeEntity;

import java.util.UUID;

public interface ReasonCodeCommandService {
    ReasonCodeEntity create(ReasonCodeEntity reasonCode);
    ReasonCodeEntity update(UUID id, ReasonCodeEntity reasonCode);
    void delete(UUID id);
}
