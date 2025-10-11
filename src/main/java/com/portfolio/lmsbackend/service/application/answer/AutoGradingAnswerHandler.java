package com.portfolio.lmsbackend.service.application.answer;

import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import static com.portfolio.lmsbackend.enums.content.quiz.AnswerStatus.GRADED;

public abstract class AutoGradingAnswerHandler extends AnswerHandler {

    @Override
    protected final void handleAnswered(Answer answer) {
        grade(answer);
        answer.setStatus(GRADED);
    }

    protected abstract void grade(Answer answer);
}
