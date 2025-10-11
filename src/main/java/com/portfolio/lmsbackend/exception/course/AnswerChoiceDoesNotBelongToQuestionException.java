package com.portfolio.lmsbackend.exception.course;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AnswerChoiceDoesNotBelongToQuestionException extends ResponseStatusException {
    private static final String MESSAGE = "The selected answer choice does not belong to the specified question";

    public AnswerChoiceDoesNotBelongToQuestionException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        log.warn(MESSAGE);
    }
}
