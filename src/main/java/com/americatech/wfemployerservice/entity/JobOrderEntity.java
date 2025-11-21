package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_orders")
@Setter
@Getter
public class JobOrderEntity extends Auditable {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id", nullable = false)
    private EmployerEntity employer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demand_letter_id")
    private DemandLetterEntity demandLetter;

    @Column(name = "order_number", nullable = false, length = 50, unique = true)
    private String orderNumber;

    @Column(name = "job_title", nullable = false, length = 255)
    private String jobTitle;

    @Column(name = "job_category", nullable = false, length = 100)
    private String jobCategory;

    @Column(name = "required_quantity", nullable = false)
    private Integer requiredQuantity;

    @Column(name = "filled_quantity", nullable = false)
    private Integer filledQuantity;

    @Column(name = "job_description", nullable = false, columnDefinition = "text")
    private String jobDescription;

    @Column(name = "salary_min", nullable = false, precision = 10, scale = 2)
    private BigDecimal salaryMin;

    @Column(name = "salary_max", nullable = false, precision = 10, scale = 2)
    private BigDecimal salaryMax;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "contract_duration_months", nullable = false)
    private Integer contractDurationMonths;

    @Column(name = "probation_period_months", nullable = false)
    private Integer probationPeriodMonths;

    @Column(name = "working_hours", length = 255)
    private String workingHours;

    @Column(name = "benefits", columnDefinition = "text")
    private String benefits;

    @Column(name = "required_start_date")
    private LocalDate requiredStartDate;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "validated_by", columnDefinition = "uuid")
    private UUID validatedBy;

    @Column(name = "validated_at", columnDefinition = "timestamp with time zone")
    private LocalDateTime validatedAt;
}
