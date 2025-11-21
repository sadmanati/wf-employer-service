package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;
import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployerQuotaEntityMapper extends DomainEntityMapper<EmployerQuotaModel, EmployerQuotaEntity> {
}
