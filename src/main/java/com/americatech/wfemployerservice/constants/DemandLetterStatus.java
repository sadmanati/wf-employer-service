package com.americatech.wfemployerservice.constants;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DemandLetterStatus {
    DRAFT("draft"),
    SUBMITTED("submitted"),
    UNDER_REVIEW("under_review"),
    APPROVED("approved"),
    REJECTED("rejected"),
    EXPIRED("expired");

    private final String code;

    DemandLetterStatus(String code) { this.code = code; }

    public String getCode() { return code; }

    public static boolean isValid(String value) {
        if (value == null) return false;
        return Arrays.stream(values()).anyMatch(v -> v.code.equals(value));
    }

    public static Set<String> codes() {
        return Arrays.stream(values()).map(DemandLetterStatus::getCode).collect(Collectors.toUnmodifiableSet());
    }
}
