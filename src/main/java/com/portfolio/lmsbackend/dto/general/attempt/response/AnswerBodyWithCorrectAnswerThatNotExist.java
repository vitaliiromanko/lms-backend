package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

public class AnswerBodyWithCorrectAnswerThatNotExist extends AnswerBodyWithCorrectAnswer {
    public AnswerBodyWithCorrectAnswerThatNotExist(Answer answer) {
        super(answer);
    }

    @JsonCreator
    protected AnswerBodyWithCorrectAnswerThatNotExist(
            @JsonProperty("question_type") QuestionType questionType,
            @JsonProperty("question_body") QuestionBody questionBody,
            @JsonProperty("user_answer") AnswerContent userAnswer
    ) {
        super(questionType, questionBody, userAnswer);
    }
}
