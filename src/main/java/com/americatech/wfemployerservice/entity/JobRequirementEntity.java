package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "job_requirements")
@Setter
@Getter
public class JobRequirementEntity extends Auditable {

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
    private BigDecimal weightage;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
}
