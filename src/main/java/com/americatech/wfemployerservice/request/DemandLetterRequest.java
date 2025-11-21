package com.americatech.wfemployerservice.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DemandLetterRequest {
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
    private String reviewNotes;
    private String rejectionReasonCode;
}
