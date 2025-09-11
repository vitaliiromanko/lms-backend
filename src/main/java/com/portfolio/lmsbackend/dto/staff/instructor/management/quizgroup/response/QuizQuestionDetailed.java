package com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.ChoiceQuestionDetailed;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.FillTheGapsQuestionDetailed;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.QuestionDetailed;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.TextLongQuestionDetailed;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;

import java.util.UUID;

import static com.portfolio.lmsbackend.service.application.helper.QuestionServiceHelper.mapQuestionTo;

public record QuizQuestionDetailed(
        @JsonView(Views.General.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.General.class)
        @JsonProperty("question")
        QuestionDetailed question,
        @JsonView(Views.General.class)
        @JsonProperty("max_score")
        Double maxScore
) {
    public QuizQuestionDetailed(QuizQuestion quizQuestion) {
        this(
                quizQuestion.getId(),
                mapQuestionTo(quizQuestion.getQuestion(),
                        ChoiceQuestionDetailed::new,
                        ChoiceQuestionDetailed::new,
                        FillTheGapsQuestionDetailed::new,
                        TextLongQuestionDetailed::new),
                quizQuestion.getMaxScore()
        );
    }
}
