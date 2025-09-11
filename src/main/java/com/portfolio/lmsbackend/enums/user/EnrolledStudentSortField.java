package com.portfolio.lmsbackend.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnrolledStudentSortField {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email"),
    ENROLLED_AT("enrolledAt");

    private final String field;
}
