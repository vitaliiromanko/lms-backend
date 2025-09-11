package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class QuestionNotFoundException extends ResponseStatusException {
    private static final String MESSAGE = "Question not found";

    public QuestionNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
        log.warn(MESSAGE);
    }
}
