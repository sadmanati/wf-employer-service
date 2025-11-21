package com.americatech.wfemployerservice.repository;

import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobOrderHistoryRepository extends JpaRepository<JobOrderHistoryEntity, UUID> {
}
