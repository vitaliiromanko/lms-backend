package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class CategoryHasCoursesException extends ResponseStatusException {
    private final static String MESSAGE = "Cannot delete category because it has assigned courses";

    public CategoryHasCoursesException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
