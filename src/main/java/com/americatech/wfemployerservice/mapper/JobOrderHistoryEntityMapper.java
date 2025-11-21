package com.americatech.wfemployerservice.mapper;


import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;
import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobOrderHistoryEntityMapper extends DomainEntityMapper<JobOrderHistoryModel, JobOrderHistoryEntity> {

}
