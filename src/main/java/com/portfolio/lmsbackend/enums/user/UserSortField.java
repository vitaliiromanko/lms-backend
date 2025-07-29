package com.portfolio.lmsbackend.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSortField {
    TYPE("type"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email"),
    EMAIL_VERIFIED("emailVerified"),
    STAFF_ROLE("role"),
    STATUS("status"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String field;
}
