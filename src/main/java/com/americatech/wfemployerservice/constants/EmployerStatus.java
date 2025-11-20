package com.americatech.wfemployerservice.constants;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum EmployerStatus {
    ACTIVE("active"),
    SUSPENDED("suspended"),
    UNDER_INVESTIGATION("under_investigation");

    private final String code;

    EmployerStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isValid(String value) {
        if (value == null) return false;
        return Arrays.stream(values()).anyMatch(v -> v.code.equals(value));
    }

    public static Set<String> codes() {
        return Arrays.stream(values()).map(EmployerStatus::getCode).collect(Collectors.toUnmodifiableSet());
    }
}
