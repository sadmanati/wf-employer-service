package com.americatech.wfemployerservice.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class JobOrderHistoryModel {

    private UUID id;
    private UUID jobOrderId;
    private String previousStatus;
    private String newStatus;
    private UUID changedBy;
    private String reasonCode;
    private String notes;
    private LocalDateTime createdAt;
}
