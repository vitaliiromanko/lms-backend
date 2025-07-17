package com.portfolio.lmsbackend.exception.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class UserNotFoundException extends ResponseStatusException {
    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
        log.warn(MESSAGE);
    }
}
