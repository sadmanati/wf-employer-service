package com.americatech.wfemployerservice.mapper;

import java.util.List;

public interface DomainEntityMapper<T, S> {

    T entityToDomainModel(S entity);

    S domainModelToEntity(T domainModel);

    List<T> entityToDomainModel(List<S> entity);

    List<S> domainModelToEntity(List<T> model);

}