package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record NewQuizQuestion(
        @NotBlank
        @JsonProperty("question_id")
        String questionId,
        @NotNull
        @PositiveOrZero
        @JsonProperty("max_score")
        Double maxScore
) {
}
