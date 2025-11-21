package com.americatech.wfemployerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandLetterModel {
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
    private OffsetDateTime reviewedAt;
    private String reviewNotes;
    private String rejectionReasonCode;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
