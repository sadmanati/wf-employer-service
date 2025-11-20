package com.americatech.wfemployerservice.repository;

import com.americatech.wfemployerservice.entity.EmployerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployerRepository extends JpaRepository<EmployerEntity, UUID> {
}
