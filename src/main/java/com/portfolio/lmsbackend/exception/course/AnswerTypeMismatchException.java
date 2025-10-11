package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AnswerTypeMismatchException extends ResponseStatusException {
    private final static String MESSAGE = "The submitted answer type does not match the type stored in the database";

    public AnswerTypeMismatchException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
