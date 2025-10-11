package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptDtoStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class GetAttemptResponse {
    private final UUID id;
    private final Integer attemptNumber;
    private final AttemptDtoStatus status;
    private final UUID quizId;
    private final LocalDateTime startedAt;

    protected GetAttemptResponse(Attempt attempt, Integer attemptNumber, AttemptDtoStatus status) {
        this(
                attempt.getId(),
                attemptNumber,
                status,
                attempt.getQuiz().getId(),
                attempt.getStartedAt()
        );
    }

    @JsonCreator
    protected GetAttemptResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("attempt_number") Integer attemptNumber,
            @JsonProperty("status") AttemptDtoStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("started_at") LocalDateTime startedAt
    ) {
        this.id = id;
        this.attemptNumber = attemptNumber;
        this.status = status;
        this.quizId = quizId;
        this.startedAt = startedAt;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("attempt_number")
    public Integer attemptNumber() {
        return attemptNumber;
    }

    @JsonProperty("status")
    public AttemptDtoStatus status() {
        return status;
    }

    @JsonProperty("quiz_id")
    public UUID quizId() {
        return quizId;
    }

    @JsonProperty("started_at")
    public LocalDateTime startedAt() {
        return startedAt;
    }
}
