package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "job_order_history")
@Setter
@Getter
public class JobOrderHistoryEntity extends Auditable {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_order_id", nullable = false)
    private JobOrderEntity jobOrder;

    @Column(name = "previous_status", length = 30)
    private String previousStatus; // nullable

    @Column(name = "new_status", nullable = false, length = 30)
    private String newStatus;

    @Column(name = "changed_by", nullable = false, columnDefinition = "uuid")
    private UUID changedBy;

    @Column(name = "reason_code", length = 50)
    private String reasonCode;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;

}
