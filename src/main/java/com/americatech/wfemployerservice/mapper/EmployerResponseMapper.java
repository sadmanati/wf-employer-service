package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.response.EmployerResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployerResponseMapper extends DomainResponseMapper<EmployerModel, EmployerResponse> {

    @Override
    EmployerModel responseModelToDomainModel(EmployerResponse responseModel);

    @Override
    EmployerResponse domainModelToResponseModel(EmployerModel domainModel);

    @Override
    List<EmployerModel> responseModelToDomainModel(List<EmployerResponse> responseModel);

    @Override
    List<EmployerResponse> domainModelToResponseModel(List<EmployerModel> responseModel);
}
