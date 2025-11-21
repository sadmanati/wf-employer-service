package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.response.EmployerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployerResponseMapper extends DomainResponseMapper<EmployerModel, EmployerResponse> {

}
