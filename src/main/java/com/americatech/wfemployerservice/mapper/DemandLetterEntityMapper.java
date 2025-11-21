package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.DemandLetterModel;
import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DemandLetterEntityMapper extends DomainEntityMapper<DemandLetterModel, DemandLetterEntity> {

}
