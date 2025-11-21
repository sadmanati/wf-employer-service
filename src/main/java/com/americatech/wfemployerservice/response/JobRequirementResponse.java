package com.americatech.wfemployerservice.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class JobRequirementResponse {
    private UUID id;
    private UUID jobOrderId;
    private String requirementType;
    private String category;
    private String name;
    private String level;
    private Integer minYearsExperience;
    private BigDecimal weightage;
    private String metadata;
    private LocalDateTime createdAt;
}
