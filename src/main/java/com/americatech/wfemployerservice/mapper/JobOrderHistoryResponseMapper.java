package com.americatech.wfemployerservice.mapper;


import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;
import com.americatech.wfemployerservice.response.JobOrderHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobOrderHistoryResponseMapper extends DomainResponseMapper<JobOrderHistoryModel, JobOrderHistoryResponse> {

}
