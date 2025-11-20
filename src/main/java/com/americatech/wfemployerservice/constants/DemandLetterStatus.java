package com.americatech.wfemployerservice.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum DemandLetterStatus {
    DRAFT("draft"),
    SUBMITTED("submitted"),
    UNDER_REVIEW("under_review"),
    APPROVED("approved"),
    REJECTED("rejected"),
    EXPIRED("expired");

    private final String code;

    DemandLetterStatus(String code) { this.code = code; }

}
