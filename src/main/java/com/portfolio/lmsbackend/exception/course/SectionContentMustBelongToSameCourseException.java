package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class SectionContentMustBelongToSameCourseException extends ResponseStatusException {
    private final static String MESSAGE = "Section content must belong to same course";

    public SectionContentMustBelongToSameCourseException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, MESSAGE);
        log.warn(MESSAGE);
    }
}
