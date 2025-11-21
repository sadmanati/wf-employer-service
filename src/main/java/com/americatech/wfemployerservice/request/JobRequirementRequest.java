package com.americatech.wfemployerservice.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class JobRequirementRequest {
    private UUID jobOrderId;
    private String requirementType;
    private String category;
    private String name;
    private String level;
    private Integer minYearsExperience;
    private BigDecimal weightage;
    private String metadata;
}
