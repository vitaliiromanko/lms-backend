package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class QuestionGroupIsArchivedException extends ResponseStatusException {
    private static final String MESSAGE = "Question group is archived";

    public QuestionGroupIsArchivedException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
