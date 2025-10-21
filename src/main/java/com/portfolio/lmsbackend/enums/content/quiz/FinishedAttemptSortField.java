package com.portfolio.lmsbackend.enums.content.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinishedAttemptSortField {
    STATUS("status"),
    STARTED_AT("startedAt"),
    FINISHED_AT("finishedAt"),

    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email");

    private final String field;
}
