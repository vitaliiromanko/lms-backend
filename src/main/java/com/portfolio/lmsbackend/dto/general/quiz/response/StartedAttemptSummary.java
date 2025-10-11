package com.portfolio.lmsbackend.dto.general.quiz.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.UUID;

public class StartedAttemptSummary extends AttemptSummary {
    public StartedAttemptSummary(Attempt attempt) {
        super(attempt);
    }

    @JsonCreator
    protected StartedAttemptSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") AttemptStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, status, quizId, startedAt, createdAt, updatedAt);
    }
}
