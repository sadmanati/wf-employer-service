package com.americatech.wfemployerservice.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
