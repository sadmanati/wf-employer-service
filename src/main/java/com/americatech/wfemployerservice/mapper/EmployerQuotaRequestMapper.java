package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;
import com.americatech.wfemployerservice.request.EmployerQuotaRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployerQuotaRequestMapper extends DomainRequestMapper<EmployerQuotaModel, EmployerQuotaRequest> {

}
