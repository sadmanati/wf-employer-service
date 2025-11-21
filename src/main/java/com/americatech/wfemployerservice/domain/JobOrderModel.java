package com.americatech.wfemployerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderModel {
    private UUID id;
    private UUID employerId;
    private UUID demandLetterId;
    private String orderNumber;
    private String jobTitle;
    private String jobCategory;
    private Integer requiredQuantity;
    private Integer filledQuantity;
    private String jobDescription;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String currency;
    private Integer contractDurationMonths;
    private Integer probationPeriodMonths;
    private String workingHours;
    private String benefits;
    private LocalDate requiredStartDate;
    private String status;
    private UUID validatedBy;
    private LocalDateTime validatedAt;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
