package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class QuestionGroupTopicHierarchyConflictException extends ResponseStatusException {
    private final static String MESSAGE = "Topic cannot have one of its children as parent";

    public QuestionGroupTopicHierarchyConflictException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
