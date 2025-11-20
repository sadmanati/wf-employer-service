package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.Employer;
import com.americatech.wfemployerservice.entity.EmployerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployerEntityMapper extends DomainEntityMapper<Employer, EmployerEntity> {

    @Override
    Employer entityToDomainModel(EmployerEntity entity);

    @Override
    EmployerEntity domainModelToEntity(Employer domainModel);

    @Override
    List<Employer> entityToDomainModel(List<EmployerEntity> entity);

    @Override
    List<EmployerEntity> domainModelToEntity(List<Employer> model);
}
