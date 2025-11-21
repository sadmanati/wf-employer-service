package com.americatech.wfemployerservice.repository;

import com.americatech.wfemployerservice.entity.ReasonCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReasonCodeRepository extends JpaRepository<ReasonCodeEntity, UUID> {
    Optional<ReasonCodeEntity> findByCode(String code);
}
