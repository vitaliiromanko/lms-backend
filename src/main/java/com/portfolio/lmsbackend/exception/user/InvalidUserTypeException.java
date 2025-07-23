package com.portfolio.lmsbackend.exception.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidUserTypeException extends ResponseStatusException {
    private final static String MESSAGE = "User type is invalid";

    public InvalidUserTypeException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
