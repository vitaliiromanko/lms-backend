package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ScoreExceedsMaximumException extends ResponseStatusException {
    private static final String MESSAGE = "Score cannot exceed the maximum allowed value";

    public ScoreExceedsMaximumException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
