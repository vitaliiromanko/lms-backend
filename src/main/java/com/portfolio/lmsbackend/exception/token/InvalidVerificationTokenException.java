package com.portfolio.lmsbackend.exception.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidVerificationTokenException extends ResponseStatusException {
    private static final String MESSAGE = "Verification token is invalid";

    public InvalidVerificationTokenException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
