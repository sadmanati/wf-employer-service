package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.Employer;
import com.americatech.wfemployerservice.response.EmployerResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployerResponseMapper extends DomainResponseMapper<Employer, EmployerResponse> {

    @Override
    Employer responseModelToDomainModel(EmployerResponse responseModel);

    @Override
    EmployerResponse domainModelToResponseModel(Employer domainModel);

    @Override
    List<Employer> responseModelToDomainModel(List<EmployerResponse> responseModel);

    @Override
    List<EmployerResponse> domainModelToResponseModel(List<Employer> responseModel);
}
