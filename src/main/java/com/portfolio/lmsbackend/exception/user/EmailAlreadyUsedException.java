package com.portfolio.lmsbackend.exception.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class EmailAlreadyUsedException extends ResponseStatusException {
    private final static String MESSAGE = "Email is already used";

    public EmailAlreadyUsedException() {
        super(HttpStatus.CONFLICT, MESSAGE);
        log.warn(MESSAGE);
    }
}
