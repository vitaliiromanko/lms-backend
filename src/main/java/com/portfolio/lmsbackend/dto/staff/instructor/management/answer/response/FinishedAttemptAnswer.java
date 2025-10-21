package com.portfolio.lmsbackend.dto.staff.instructor.management.answer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.answer.response.AnswerBodyWithCorrectAnswer;
import com.portfolio.lmsbackend.dto.answer.response.AnswerBodyWithCorrectAnswerThatExists;
import com.portfolio.lmsbackend.dto.answer.response.AnswerBodyWithCorrectAnswerThatNotExist;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.util.UUID;

public record FinishedAttemptAnswer(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("answer_body")
        AnswerBodyWithCorrectAnswer answerBody,
        @JsonProperty("score")
        Double score,
        @JsonProperty("max_score")
        Double maxScore
) {
    public FinishedAttemptAnswer(Answer answer) {
        this(
                answer.getId(),
                getAnswerBodyWithCorrectAnswer(answer),
                answer.getScore(),
                answer.getQuizQuestion().getMaxScore()

        );
    }

    private static AnswerBodyWithCorrectAnswer getAnswerBodyWithCorrectAnswer(Answer answer) {
        return switch (answer.getQuizQuestion().getQuestion().getType()) {
            case SINGLE_CHOICE, MULTIPLE_CHOICE, FILL_THE_GAPS -> new AnswerBodyWithCorrectAnswerThatExists(answer);
            case TEXT_LONG -> new AnswerBodyWithCorrectAnswerThatNotExist(answer);
        };
    }
}
