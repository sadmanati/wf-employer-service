package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.request.JobRequirementRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobRequirementRequestMapper extends DomainRequestMapper<JobRequirementModel, JobRequirementRequest> {

    @Override
    JobRequirementModel requestModelToDomainModel(JobRequirementRequest requestModel);

    @Override
    JobRequirementRequest domainModelToRequestModel(JobRequirementModel domainModel);

    @Override
    List<JobRequirementModel> requestModelToDomainModel(List<JobRequirementRequest> requestModel);

    @Override
    List<JobRequirementRequest> domainModelToRequestModel(List<JobRequirementModel> domainModel);
}
