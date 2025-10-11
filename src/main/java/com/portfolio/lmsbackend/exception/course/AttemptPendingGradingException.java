package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AttemptPendingGradingException extends ResponseStatusException {
    private final static String MESSAGE = "Attempt is pending grading and cannot be accessed yet";

    public AttemptPendingGradingException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
