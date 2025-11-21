package com.americatech.wfemployerservice.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EmployerQuotaRequest {
    private UUID employerId;
    private String jobCategory;
    private Integer totalQuota;
    private Integer usedQuota;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private String mohreReference;
}
