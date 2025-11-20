package com.americatech.wfemployerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployerModel {
    private UUID id;
    private UUID userId;
    private String companyName;
    private String tradeLicenseNumber;
    private LocalDate tradeLicenseExpiry;
    private String mohreEstablishmentId;
    private String taxRegistrationNumber;
    private String address;
    private String city;
    private String emirate;
    private String status;
    private String contactDetails; // json string
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
