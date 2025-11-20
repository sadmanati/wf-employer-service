package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.Employer;
import com.americatech.wfemployerservice.request.EmployerRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployerRequestMapper extends DomainRequestMapper<Employer, EmployerRequest> {

    @Override
    Employer requestModelToDomainModel(EmployerRequest requestModel);

    @Override
    EmployerRequest domainModelToRequestModel(Employer domainModel);

    @Override
    List<Employer> requestModelToDomainModel(List<EmployerRequest> requestModel);

    @Override
    List<EmployerRequest> domainModelToRequestModel(List<Employer> domainModel);
}
