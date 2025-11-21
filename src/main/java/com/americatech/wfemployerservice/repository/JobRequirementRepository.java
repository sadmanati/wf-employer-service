package com.americatech.wfemployerservice.repository;

import com.americatech.wfemployerservice.entity.JobRequirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRequirementRepository extends JpaRepository<JobRequirementEntity, UUID> {
}
