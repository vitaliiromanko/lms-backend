package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NewQuiz(
        @NotEmpty
        @JsonProperty("new_quiz_questions")
        List<@Valid NewQuizQuestion> newQuizQuestions,
        @NotNull
        @JsonProperty("shuffle_questions")
        Boolean shuffleQuestions
) {
}
