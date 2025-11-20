package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reason_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReasonCodeEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "applicable_to", nullable = false, length = 30)
    private String applicableTo; // employer, workforce_uae

    @Column(name = "action_type", nullable = false, length = 20)
    private String actionType; // reject, return, withdraw

    @Column(name = "workflow_consequence", nullable = false, length = 30)
    private String workflowConsequence; // rework, escalation, hard_stop, none

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime updatedAt;
}
