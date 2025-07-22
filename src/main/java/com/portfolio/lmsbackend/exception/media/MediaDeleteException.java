package com.portfolio.lmsbackend.exception.media;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class MediaDeleteException extends ResponseStatusException {
    private final static String MESSAGE = "Failed to delete media file";

    public MediaDeleteException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE);
        log.warn(MESSAGE);
    }
}
