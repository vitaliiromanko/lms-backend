package com.portfolio.lmsbackend.service.application.answer;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.util.Set;

public interface AnswerHandler {
    Set<QuestionType> getSupportedQuestionTypes();

    void handle(Answer answer);
}
