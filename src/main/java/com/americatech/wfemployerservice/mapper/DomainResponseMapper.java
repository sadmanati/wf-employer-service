package com.americatech.wfemployerservice.mapper;

import java.util.List;


public interface DomainResponseMapper<T, S> {

    T responseModelToDomainModel(S responseModel);

    S domainModelToResponseModel(T domainModel);

    List<T> responseModelToDomainModel(List<S> responseModel);

    List<S> domainModelToResponseModel(List<T> responseModel);


}