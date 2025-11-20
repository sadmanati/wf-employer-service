package com.americatech.wfemployerservice.repository;

import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DemandLetterRepository extends JpaRepository<DemandLetterEntity, UUID> {
}
