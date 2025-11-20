package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_requirements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequirementEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_order_id", nullable = false)
    private JobOrderEntity jobOrder;

    @Column(name = "requirement_type", nullable = false, length = 20)
    private String requirementType; // mandatory, preferred

    @Column(name = "category", nullable = false, length = 50)
    private String category; // skill, language, certification, education, experience

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "min_years_experience")
    private Integer minYearsExperience;

    @Column(name = "weightage", precision = 5, scale = 2)
    private BigDecimal weightage; // 0.00 .. 1.00

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;
}
