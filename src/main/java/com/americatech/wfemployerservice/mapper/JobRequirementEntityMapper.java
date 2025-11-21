package com.americatech.wfemployerservice.mapper;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.entity.JobOrderEntity;
import com.americatech.wfemployerservice.entity.JobRequirementEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface JobRequirementEntityMapper extends DomainEntityMapper<JobRequirementModel, JobRequirementEntity> {

    @Override
    @Mapping(target = "jobOrderId", source = "jobOrder.id")
    JobRequirementModel entityToDomainModel(JobRequirementEntity entity);

    @Override
    @Mapping(target = "jobOrder", source = "jobOrderId")
    JobRequirementEntity domainModelToEntity(JobRequirementModel domainModel);

    @Override
    List<JobRequirementModel> entityToDomainModel(List<JobRequirementEntity> entity);

    @Override
    List<JobRequirementEntity> domainModelToEntity(List<JobRequirementModel> model);

    default JobOrderEntity map(UUID jobOrderId) {
        if (jobOrderId == null) return null;
        JobOrderEntity e = new JobOrderEntity();
        e.setId(jobOrderId);
        return e;
    }
}
