package com.portfolio.lmsbackend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringsHelper {
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    public static final String PASSWORD_REGEXP = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";

    public static final String SUCCESS_MESSAGE = "Success";

    public static final String ORIGIN_HEADER = "Origin";

    public static final Pattern MEDIA_PLACEHOLDER =
            Pattern.compile("(?<!\\\\)\\[media:(?<type>\\w+):(?<id>\\d+)]");

    public static final String WS_QUEUE_PREFIX = "/queue";
    public static final String WS_USER_PREFIX = "/user";
    public static final String WS_APP_PREFIX = "/app";

    public static final String ERRORS_QUEUE_PREFIX = WS_QUEUE_PREFIX + "/errors";
    public static final String ATTEMPT_QUEUE_PREFIX = WS_QUEUE_PREFIX + "/attempt";
    public static final String ATTEMPT_QUEUE_TEMPLATE = ATTEMPT_QUEUE_PREFIX + "/%s";
    public static final String ATTEMPT_FINISHED_MESSAGE = "Your attempt is finished!";
}
