package com.americatech.wfemployerservice.mapper;


import com.americatech.wfemployerservice.domain.JobOrderModel;
import com.americatech.wfemployerservice.request.JobOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobOrderRequestMapper extends DomainRequestMapper<JobOrderModel, JobOrderRequest> {

}

