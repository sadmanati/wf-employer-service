package com.americatech.wfemployerservice.response;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class JobOrderResponse {

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
    private OffsetDateTime validatedAt;
    private UUID createdBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

