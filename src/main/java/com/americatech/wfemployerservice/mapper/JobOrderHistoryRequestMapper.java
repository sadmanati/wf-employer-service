package com.americatech.wfemployerservice.mapper;


import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;
import com.americatech.wfemployerservice.request.JobOrderHistoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobOrderHistoryRequestMapper extends DomainRequestMapper<JobOrderHistoryModel, JobOrderHistoryRequest> {

}
