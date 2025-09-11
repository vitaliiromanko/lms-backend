package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class CourseNotFoundException extends ResponseStatusException {
    private static final String MESSAGE = "Course not found";

    public CourseNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
        log.warn(MESSAGE);
    }
}
