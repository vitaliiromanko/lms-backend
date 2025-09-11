package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class StudentsAlreadyEnrolledException extends ResponseStatusException {
    private final static String MESSAGE = "Some students are already enrolled in the course";

    public StudentsAlreadyEnrolledException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
