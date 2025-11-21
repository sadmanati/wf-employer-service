package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "reason_codes")
@Setter
@Getter
public class ReasonCodeEntity extends Auditable {

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
    private String applicableTo;

    @Column(name = "action_type", nullable = false, length = 20)
    private String actionType;

    @Column(name = "workflow_consequence", nullable = false, length = 30)
    private String workflowConsequence;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}
