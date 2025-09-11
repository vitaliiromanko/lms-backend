package com.portfolio.lmsbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidPositionException extends ResponseStatusException {
    private static final String MESSAGE = "Position is invalid";

    public InvalidPositionException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
