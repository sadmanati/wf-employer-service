package com.americatech.wfemployerservice.request;

import lombok.Data;

import java.util.UUID;

@Data
public class JobOrderHistoryRequest {

    private UUID jobOrderId;
    private String previousStatus;
    private String newStatus;
    private UUID changedBy;
    private String reasonCode;
    private String notes;
}
