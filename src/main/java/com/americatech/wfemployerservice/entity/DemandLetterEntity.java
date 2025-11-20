package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "demand_letters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandLetterEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id", nullable = false)
    private EmployerEntity employer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_quota_id", nullable = false)
    private EmployerQuotaEntity employerQuota;

    @Column(name = "demand_letter_number", nullable = false, length = 50, unique = true)
    private String demandLetterNumber;

    @Column(name = "job_category", nullable = false, length = 100)
    private String jobCategory;

    @Column(name = "requested_quantity", nullable = false)
    private Integer requestedQuantity;

    @Column(name = "salary_offered", nullable = false, precision = 10, scale = 2)
    private BigDecimal salaryOffered;

    @Column(name = "contract_duration_months", nullable = false)
    private Integer contractDurationMonths;

    @Column(name = "terms_and_conditions", columnDefinition = "text")
    private String termsAndConditions;

    @Column(name = "document_url", length = 500)
    private String documentUrl;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "reviewed_by", columnDefinition = "uuid")
    private UUID reviewedBy;

    @Column(name = "reviewed_at", columnDefinition = "timestamp with time zone")
    private OffsetDateTime reviewedAt;

    @Column(name = "review_notes", columnDefinition = "text")
    private String reviewNotes;

    @Column(name = "rejection_reason_code", length = 50)
    private String rejectionReasonCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime updatedAt;
}
