package com.americatech.wfemployerservice.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class EmployerQuotaResponse {
    private UUID id;
    private UUID employerId;
    private String jobCategory;
    private Integer totalQuota;
    private Integer usedQuota;
    private Integer availableQuota;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private String mohreReference;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
