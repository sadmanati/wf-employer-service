package com.americatech.wfemployerservice.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DemandLetterResponse {
    private UUID id;
    private UUID employerId;
    private UUID employerQuotaId;
    private String demandLetterNumber;
    private String jobCategory;
    private Integer requestedQuantity;
    private BigDecimal salaryOffered;
    private Integer contractDurationMonths;
    private String termsAndConditions;
    private String documentUrl;
    private String status;
    private UUID reviewedBy;
    private LocalDateTime reviewedAt;
    private String reviewNotes;
    private String rejectionReasonCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
