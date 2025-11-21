package com.americatech.wfemployerservice.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EmployerRequest {
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
}
