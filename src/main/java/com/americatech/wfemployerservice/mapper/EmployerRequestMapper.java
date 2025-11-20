package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.EmployerModel;
import com.americatech.wfemployerservice.request.EmployerRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployerRequestMapper extends DomainRequestMapper<EmployerModel, EmployerRequest> {

    @Override
    EmployerModel requestModelToDomainModel(EmployerRequest requestModel);

    @Override
    EmployerRequest domainModelToRequestModel(EmployerModel domainModel);

    @Override
    List<EmployerModel> requestModelToDomainModel(List<EmployerRequest> requestModel);

    @Override
    List<EmployerRequest> domainModelToRequestModel(List<EmployerModel> domainModel);
}
