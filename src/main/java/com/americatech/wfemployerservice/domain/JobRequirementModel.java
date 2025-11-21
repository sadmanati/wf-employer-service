package com.americatech.wfemployerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequirementModel {
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
