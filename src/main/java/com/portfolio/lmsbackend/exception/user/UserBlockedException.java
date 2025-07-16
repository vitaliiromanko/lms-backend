package com.portfolio.lmsbackend.exception.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class UserBlockedException extends ResponseStatusException {
    private static final String MESSAGE = "User is blocked";

    public UserBlockedException() {
        super(HttpStatus.FORBIDDEN, MESSAGE);
        log.warn(MESSAGE);
    }
}
