package com.portfolio.lmsbackend.exception.media;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidMediaPlaceholderException extends ResponseStatusException {
    private static final String MESSAGE = "Media placeholder is invalid";

    public InvalidMediaPlaceholderException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
