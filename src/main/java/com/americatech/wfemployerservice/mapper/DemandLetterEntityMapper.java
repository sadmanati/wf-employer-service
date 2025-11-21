package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.DemandLetterModel;
import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DemandLetterEntityMapper extends DomainEntityMapper<DemandLetterModel, DemandLetterEntity> {

}
