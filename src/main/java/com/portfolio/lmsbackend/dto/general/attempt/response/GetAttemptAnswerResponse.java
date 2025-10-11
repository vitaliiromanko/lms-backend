package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AnswerDtoStatus;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.util.UUID;

public abstract class GetAttemptAnswerResponse {
    private final UUID id;
    private final AnswerDtoStatus status;

    protected GetAttemptAnswerResponse(Answer answer, AnswerDtoStatus status) {
        this(
                answer.getId(),
                status
        );
    }

    @JsonCreator
    protected GetAttemptAnswerResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") AnswerDtoStatus status
    ) {
        this.id = id;
        this.status = status;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("status")
    public AnswerDtoStatus status() {
        return status;
    }
}
