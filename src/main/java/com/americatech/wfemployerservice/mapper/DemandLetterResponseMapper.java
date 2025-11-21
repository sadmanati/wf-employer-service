package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.DemandLetterModel;
import com.americatech.wfemployerservice.response.DemandLetterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DemandLetterResponseMapper extends DomainResponseMapper<DemandLetterModel, DemandLetterResponse> {

}
