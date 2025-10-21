package com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.staff.instructor.management.user.response.UserSummary;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.enums.content.quiz.FinishedAttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class FinishedAttemptSummary {
    private final UUID id;
    private final FinishedAttemptStatus status;
    private final UUID quizId;
    private final UserSummary performer;
    private final LocalDateTime startedAt;
    private final LocalDateTime finishedAt;

    protected FinishedAttemptSummary(Attempt attempt) {
        this(
                attempt.getId(),
                getStatus(attempt.getStatus()),
                attempt.getQuiz().getId(),
                new UserSummary(attempt.getUser()),
                attempt.getStartedAt(),
                attempt.getFinishedAt()
        );
    }

    @JsonCreator
    protected FinishedAttemptSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") FinishedAttemptStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("performer") UserSummary performer,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("finished_at") LocalDateTime finishedAt
    ) {
        this.id = id;
        this.status = status;
        this.quizId = quizId;
        this.performer = performer;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    private static FinishedAttemptStatus getStatus(AttemptStatus status) {
        return switch (status) {
            case STARTED -> throw new IllegalStateException(
                    "Attempt is still in progress and cannot be marked as finished");
            case PENDING_GRADING -> FinishedAttemptStatus.PENDING_GRADING;
            case GRADED -> FinishedAttemptStatus.GRADED;
        };
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("status")
    public FinishedAttemptStatus status() {
        return status;
    }

    @JsonProperty("quiz_id")
    public UUID quizId() {
        return quizId;
    }

    @JsonProperty("performer")
    public UserSummary performer() {
        return performer;
    }

    @JsonProperty("started_at")
    public LocalDateTime startedAt() {
        return startedAt;
    }

    @JsonProperty("finished_at")
    public LocalDateTime finishedAt() {
        return finishedAt;
    }
}
