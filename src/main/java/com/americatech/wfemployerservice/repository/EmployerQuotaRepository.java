package com.americatech.wfemployerservice.repository;

import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployerQuotaRepository extends JpaRepository<EmployerQuotaEntity, UUID> {
}
