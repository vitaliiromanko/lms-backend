package com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.staff.instructor.management.user.response.UserSummary;
import com.portfolio.lmsbackend.enums.content.quiz.FinishedAttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetFinishedAttemptsByQuizGroupResponse extends FinishedAttemptSummary {
    public GetFinishedAttemptsByQuizGroupResponse(Attempt attempt) {
        super(attempt);
    }

    @JsonCreator
    protected GetFinishedAttemptsByQuizGroupResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") FinishedAttemptStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("performer") UserSummary performer,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("finished_at") LocalDateTime finishedAt
    ) {
        super(id, status, quizId, performer, startedAt, finishedAt);
    }
}
