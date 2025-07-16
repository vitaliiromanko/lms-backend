package com.portfolio.lmsbackend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringsHelper {
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    public static final String STAFF_PASSWORD_REGEXP = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
    public static final String STUDENT_PASSWORD_REGEXP = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";

    public static final String SUCCESS_MESSAGE = "Success";

    public static final String ORIGIN_HEADER = "Origin";
}
