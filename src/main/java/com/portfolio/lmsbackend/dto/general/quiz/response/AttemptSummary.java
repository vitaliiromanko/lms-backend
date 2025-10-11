package com.portfolio.lmsbackend.dto.general.quiz.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AttemptSummary {
    @JsonView(Views.Basic.class)
    private final UUID id;

    @JsonView(Views.Basic.class)
    private final AttemptStatus status;

    @JsonView(Views.Basic.class)
    private final UUID quizId;

    @JsonView(Views.Basic.class)
    private final LocalDateTime startedAt;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime createdAt;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime updatedAt;

    protected AttemptSummary(Attempt attempt) {
        this(
                attempt.getId(),
                attempt.getStatus(),
                attempt.getQuiz().getId(),
                attempt.getStartedAt(),
                attempt.getCreatedAt(),
                attempt.getUpdatedAt()
        );
    }

    @JsonCreator
    protected AttemptSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") AttemptStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        this.id = id;
        this.status = status;
        this.quizId = quizId;
        this.startedAt = startedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("status")
    public AttemptStatus status() {
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

    @JsonProperty("created_at")
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @JsonProperty("updated_at")
    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
