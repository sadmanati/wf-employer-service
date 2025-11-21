package com.americatech.wfemployerservice.mapper;


import com.americatech.wfemployerservice.domain.JobOrderModel;
import com.americatech.wfemployerservice.entity.JobOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobOrderEntityMapper extends DomainEntityMapper<JobOrderModel, JobOrderEntity> {

}

