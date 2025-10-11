package com.portfolio.lmsbackend.service.application.answer.impl;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.model.content.quiz.answer.SingleChoiceAnswer;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.service.application.answer.AutoGradingAnswerHandler;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.SINGLE_CHOICE;

@Service
public class SingleChoiceAnswerHandler extends AutoGradingAnswerHandler {
    @Override
    public Set<QuestionType> getSupportedQuestionTypes() {
        return Set.of(SINGLE_CHOICE);
    }

    @Override
    protected void grade(Answer answer) {
        SingleChoiceAnswer singleChoiceAnswer = (SingleChoiceAnswer) answer;
        ChoiceOption selectedOption = singleChoiceAnswer.getSelectedOption();

        if (Boolean.FALSE.equals(selectedOption.getCorrect())) {
            singleChoiceAnswer.setScore(getMinScore());
        } else {
            singleChoiceAnswer.setScore(getMaxScore(singleChoiceAnswer));
        }
    }
}
