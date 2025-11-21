package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.request.EmployerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployerRequestMapper extends DomainRequestMapper<EmployerModel, EmployerRequest> {

}
