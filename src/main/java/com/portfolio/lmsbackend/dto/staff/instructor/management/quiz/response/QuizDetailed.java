package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response.QuizQuestionDetailed;
import com.portfolio.lmsbackend.model.content.quiz.Quiz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record QuizDetailed(
        @JsonView(Views.General.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.General.class)
        @JsonProperty("quiz_questions")
        List<QuizQuestionDetailed> quizQuestions,
        @JsonView(Views.General.class)
        @JsonProperty("shuffle_questions")
        Boolean shuffleQuestions,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_by")
        CreatedByResponse createdBy,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
    public QuizDetailed(Quiz quiz) {
        this(
                quiz.getId(),
                quiz.getQuizQuestions().stream()
                        .map(QuizQuestionDetailed::new)
                        .toList(),
                quiz.getShuffleQuestions(),
                new CreatedByResponse(quiz.getCreatedBy()),
                quiz.getCreatedAt()
        );
    }
}
