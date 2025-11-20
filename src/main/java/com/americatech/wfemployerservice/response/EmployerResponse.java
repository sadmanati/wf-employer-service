package com.americatech.wfemployerservice.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class EmployerResponse {
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
    private String contactDetails;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
