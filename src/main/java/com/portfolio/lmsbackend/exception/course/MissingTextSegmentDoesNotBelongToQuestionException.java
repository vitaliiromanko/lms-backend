package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class MissingTextSegmentDoesNotBelongToQuestionException extends ResponseStatusException {
    private static final String MESSAGE = "The provided missing text segment does not belong to the specified question";

    public MissingTextSegmentDoesNotBelongToQuestionException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
