package com.portfolio.lmsbackend.dto.general.quiz.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.time.LocalDateTime;
import java.util.UUID;

public class GradedAttemptSummary extends AttemptSummary {
    @JsonView(Views.Basic.class)
    private final LocalDateTime finishedAt;

    @JsonView(Views.Basic.class)
    private final Double totalScore;

    @JsonView(Views.Basic.class)
    private final Double maxScore;

    public GradedAttemptSummary(Attempt attempt) {
        super(attempt);
        this.finishedAt = attempt.getFinishedAt();
        this.totalScore = attempt.getAnswers().stream()
                .mapToDouble(Answer::getScore)
                .sum();
        this.maxScore = attempt.getAnswers().stream()
                .mapToDouble(a -> a.getQuizQuestion().getMaxScore())
                .sum();
    }

    @JsonCreator
    protected GradedAttemptSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") AttemptStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("finished_at") LocalDateTime finishedAt,
            @JsonProperty("total_score") Double totalScore,
            @JsonProperty("max_score") Double maxScore,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, status, quizId, startedAt, createdAt, updatedAt);
        this.finishedAt = finishedAt;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
    }

    @JsonProperty("finished_at")
    public LocalDateTime finishedAt() {
        return finishedAt;
    }

    @JsonProperty("total_score")
    public Double totalScore() {
        return totalScore;
    }

    @JsonProperty("max_score")
    public Double maxScore() {
        return maxScore;
    }
}
