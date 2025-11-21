package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.entity.JobRequirementEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobRequirementEntityMapper extends DomainEntityMapper<JobRequirementModel, JobRequirementEntity> {

}
