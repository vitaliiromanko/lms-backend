package com.portfolio.lmsbackend.dto.general.quiz.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus.GRADED;
import static com.portfolio.lmsbackend.service.application.helper.AttemptServiceHelper.calculateScorePct;

public record AttemptSummary(
        @JsonView(Views.Basic.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.Basic.class)
        @JsonProperty("status")
        AttemptStatus status,
        @JsonView(Views.Basic.class)
        @JsonProperty("quiz_id")
        UUID quizId,
        @JsonView(Views.Basic.class)
        @JsonProperty("score_pct")
        Double scorePct,
        @JsonView(Views.Basic.class)
        @JsonProperty("started_at")
        LocalDateTime startedAt,
        @JsonView(Views.Basic.class)
        @JsonProperty("finished_at")
        LocalDateTime finishedAt,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonView(Views.Detailed.class)
        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    public AttemptSummary(Attempt attempt) {
        this(
                attempt.getId(),
                attempt.getStatus(),
                attempt.getQuiz().getId(),
                attempt.getStatus() != GRADED
                        ? null
                        : calculateScorePct(attempt),
                attempt.getStartedAt(),
                attempt.getFinishedAt(),
                attempt.getCreatedAt(),
                attempt.getUpdatedAt()
        );
    }
}
