package com.americatech.wfemployerservice.repository;

import com.americatech.wfemployerservice.entity.JobOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobOrderRepository extends JpaRepository<JobOrderEntity, UUID> {
}
