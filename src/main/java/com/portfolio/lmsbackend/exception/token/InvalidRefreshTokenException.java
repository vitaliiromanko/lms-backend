package com.portfolio.lmsbackend.exception.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidRefreshTokenException extends ResponseStatusException {
    private static final String MESSAGE = "Refresh token is invalid";

    public InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED, MESSAGE);
        log.warn(MESSAGE);
    }
}
