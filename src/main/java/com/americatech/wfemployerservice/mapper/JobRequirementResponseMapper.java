package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.response.JobRequirementResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobRequirementResponseMapper extends DomainResponseMapper<JobRequirementModel, JobRequirementResponse> {

    @Override
    JobRequirementModel responseModelToDomainModel(JobRequirementResponse responseModel);

    @Override
    JobRequirementResponse domainModelToResponseModel(JobRequirementModel domainModel);

    @Override
    List<JobRequirementModel> responseModelToDomainModel(List<JobRequirementResponse> responseModel);

    @Override
    List<JobRequirementResponse> domainModelToResponseModel(List<JobRequirementModel> responseModel);
}
