package com.americatech.wfemployerservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "employers")
@Setter
@Getter
public class EmployerEntity extends Auditable {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private UUID userId;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "trade_license_number", nullable = false, length = 50)
    private String tradeLicenseNumber;

    @Column(name = "trade_license_expiry", nullable = false)
    private LocalDate tradeLicenseExpiry;

    @Column(name = "mohre_establishment_id", nullable = false, length = 50)
    private String mohreEstablishmentId;

    @Column(name = "tax_registration_number", length = 50)
    private String taxRegistrationNumber;

    @Column(name = "address", nullable = false, columnDefinition = "text")
    private String address;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "emirate", nullable = false, length = 50)
    private String emirate;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "contact_details", columnDefinition = "jsonb")
    private String contactDetails;

}
