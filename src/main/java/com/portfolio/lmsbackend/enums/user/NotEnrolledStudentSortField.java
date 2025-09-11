package com.portfolio.lmsbackend.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotEnrolledStudentSortField {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email");

    private final String field;
}
