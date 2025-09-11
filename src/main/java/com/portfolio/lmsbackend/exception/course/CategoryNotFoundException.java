package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class CategoryNotFoundException extends ResponseStatusException {
    private static final String MESSAGE = "Category not found";

    public CategoryNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE);
        log.warn(MESSAGE);
    }
}
