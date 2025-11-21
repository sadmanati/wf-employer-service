package com.americatech.wfemployerservice.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum EmployerStatus {
    ACTIVE("active"),
    SUSPENDED("suspended"),
    UNDER_INVESTIGATION("under_investigation");

    private final String code;

    EmployerStatus(String code) {
        this.code = code;
    }

}
