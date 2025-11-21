package com.americatech.wfemployerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployerQuotaModel {
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
