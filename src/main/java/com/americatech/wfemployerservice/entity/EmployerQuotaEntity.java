package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employer_quotas")
@Setter
@Getter
public class EmployerQuotaEntity extends Auditable {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id", nullable = false)
    private EmployerEntity employer;

    @Column(name = "job_category", nullable = false, length = 100)
    private String jobCategory;

    @Column(name = "total_quota", nullable = false)
    private Integer totalQuota;

    @Column(name = "used_quota", nullable = false)
    private Integer usedQuota;

    @Column(name = "available_quota", nullable = false)
    private Integer availableQuota;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @Column(name = "mohre_reference", length = 100)
    private String mohreReference;
}
