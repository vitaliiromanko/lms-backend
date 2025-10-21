package com.portfolio.lmsbackend.service.application.answer;

import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

public abstract class AutoGradingAnswerHandler extends AnswerHandler {

    @Override
    protected final void handleAnswered(Answer answer) {
        answer.setScore(grade(answer));
    }

    protected abstract double grade(Answer answer);
}
