package com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.staff.instructor.management.answer.response.FinishedAttemptAnswer;
import com.portfolio.lmsbackend.dto.staff.instructor.management.user.response.UserSummary;
import com.portfolio.lmsbackend.enums.content.quiz.FinishedAttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GetFinishedAttemptResponse extends FinishedAttemptSummary {
    private final Integer attemptNumber;
    private final List<FinishedAttemptAnswer> answers;

    public GetFinishedAttemptResponse(Attempt attempt, Integer attemptNumber) {
        super(attempt);
        this.attemptNumber = attemptNumber;
        this.answers = attempt.getAnswers().stream()
                .map(FinishedAttemptAnswer::new)
                .toList();
    }

    @JsonCreator
    protected GetFinishedAttemptResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("attempt_number") Integer attemptNumber,
            @JsonProperty("status") FinishedAttemptStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("performer") UserSummary performer,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("finished_at") LocalDateTime finishedAt,
            @JsonProperty("answers") List<FinishedAttemptAnswer> answers
    ) {
        super(id, status, quizId, performer, startedAt, finishedAt);
        this.attemptNumber = attemptNumber;
        this.answers = answers;
    }

    @JsonProperty("attempt_number")
    public Integer attemptNumber() {
        return attemptNumber;
    }

    @JsonProperty("answers")
    public List<FinishedAttemptAnswer> answers() {
        return answers;
    }
}
