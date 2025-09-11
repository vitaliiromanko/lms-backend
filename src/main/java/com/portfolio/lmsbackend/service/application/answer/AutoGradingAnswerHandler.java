package com.portfolio.lmsbackend.service.application.answer;

import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import static com.portfolio.lmsbackend.enums.content.quiz.AnswerStatus.GRADED;

public abstract class AutoGradingAnswerHandler implements AnswerHandler {
    private static final Double DEFAULT_MIN_SCORE = 0.0;

    @Override
    public final void handle(Answer answer) {
        grade(answer);
        setStatus(answer);
    }

    protected abstract void grade(Answer answer);

    private void setStatus(Answer answer) {
        answer.setStatus(GRADED);
    }

    protected final Double getMaxScore(Answer answer) {
        return answer.getQuizQuestion().getMaxScore();
    }

    protected final Double getMinScore() {
        return DEFAULT_MIN_SCORE;
    }
}
