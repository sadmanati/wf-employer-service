package com.americatech.wfemployerservice.mapper;


import com.americatech.wfemployerservice.domain.JobOrderModel;
import com.americatech.wfemployerservice.response.JobOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobOrderResponseMapper extends DomainResponseMapper<JobOrderModel, JobOrderResponse> {

}
