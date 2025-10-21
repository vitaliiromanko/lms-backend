package com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record GradeFinishedAttemptAnswerRequest(
        @NotNull
        @JsonProperty("answer_id")
        UUID answerId,
        @NotNull
        @PositiveOrZero
        @JsonProperty("score")
        Double score
) {
}
