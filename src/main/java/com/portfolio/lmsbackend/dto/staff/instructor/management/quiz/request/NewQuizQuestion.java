package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record NewQuizQuestion(
        @NotNull
        @JsonProperty("question_id")
        UUID questionId,
        @NotNull
        @PositiveOrZero
        @JsonProperty("max_score")
        Double maxScore
) {
}
