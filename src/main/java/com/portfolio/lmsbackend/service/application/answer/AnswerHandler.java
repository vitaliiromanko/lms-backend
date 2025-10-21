package com.portfolio.lmsbackend.service.application.answer;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.util.Set;

public abstract class AnswerHandler {
    private static final Double DEFAULT_MIN_SCORE = 0.0;

    public abstract Set<QuestionType> getSupportedQuestionTypes();

    public final void handle(Answer answer) {
        if (answer.getAnswered()) {
            handleAnswered(answer);
        } else {
            answer.setScore(getMinScore());
        }
    }

    protected abstract void handleAnswered(Answer answer);

    protected final Double getMaxScore(Answer answer) {
        return answer.getQuizQuestion().getMaxScore();
    }

    protected final Double getMinScore() {
        return DEFAULT_MIN_SCORE;
    }
}
