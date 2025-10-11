package com.portfolio.lmsbackend.service.application.answer.impl;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.service.application.answer.AnswerHandler;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.TEXT_LONG;

@Service
public class InstructorAnswerHandler extends AnswerHandler {

    @Override
    public Set<QuestionType> getSupportedQuestionTypes() {
        return Set.of(TEXT_LONG);
    }

    @Override
    protected void handleAnswered(Answer answer) {
        // For manual evaluation
    }
}
