package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.response.JobRequirementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobRequirementResponseMapper extends DomainResponseMapper<JobRequirementModel, JobRequirementResponse> {

}
