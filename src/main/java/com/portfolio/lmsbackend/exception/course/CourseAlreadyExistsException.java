package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class CourseAlreadyExistsException extends ResponseStatusException {
    private final static String MESSAGE = "Course already exists";

    public CourseAlreadyExistsException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
