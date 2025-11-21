package com.americatech.wfemployerservice.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class JobOrderHistoryResponse {

    private UUID id;
    private UUID jobOrderId;
    private String previousStatus;
    private String newStatus;
    private UUID changedBy;
    private String reasonCode;
    private String notes;
    private OffsetDateTime createdAt;
}
