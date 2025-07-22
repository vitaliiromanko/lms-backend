package com.portfolio.lmsbackend.exception.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidOldPasswordException extends ResponseStatusException {
    private final static String MESSAGE = "Old password is invalid";

    public InvalidOldPasswordException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
