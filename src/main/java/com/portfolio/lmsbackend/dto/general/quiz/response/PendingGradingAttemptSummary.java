package com.portfolio.lmsbackend.dto.general.quiz.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.UUID;

public class PendingGradingAttemptSummary extends AttemptSummary {
    @JsonView(Views.Basic.class)
    private final LocalDateTime finishedAt;

    public PendingGradingAttemptSummary(Attempt attempt) {
        super(attempt);
        this.finishedAt = attempt.getFinishedAt();
    }

    @JsonCreator
    protected PendingGradingAttemptSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") AttemptStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("finished_at") LocalDateTime finishedAt,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, status, quizId, startedAt, createdAt, updatedAt);
        this.finishedAt = finishedAt;
    }

    @JsonProperty("finished_at")
    public LocalDateTime finishedAt() {
        return finishedAt;
    }
}
