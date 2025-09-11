package com.portfolio.lmsbackend.service.application.answer.impl;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.model.content.quiz.answer.MultipleChoiceAnswer;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.model.content.quiz.question.MultipleChoiceQuestion;
import com.portfolio.lmsbackend.service.application.answer.AutoGradingAnswerHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.MULTIPLE_CHOICE;

@Service
public class MultipleChoiceAnswerHandler extends AutoGradingAnswerHandler {
    @Override
    public Set<QuestionType> getSupportedQuestionTypes() {
        return Set.of(MULTIPLE_CHOICE);
    }

    @Override
    protected void grade(Answer answer) {
        Set<ChoiceOption> correctOptions = ((MultipleChoiceQuestion) answer.getQuizQuestion().getQuestion())
                .getOptions().stream()
                .filter(ChoiceOption::getCorrect)
                .collect(Collectors.toSet());
        Set<ChoiceOption> selectedOptions = ((MultipleChoiceAnswer) answer).getSelectedOptions();

        if (selectedOptions == null || selectedOptions.isEmpty()) {
            answer.setScore(getMinScore());
        } else if (selectedOptions.equals(correctOptions)) {
            answer.setScore(getMaxScore(answer));
        } else {
            Set<ChoiceOption> correctlySelected = new HashSet<>(selectedOptions);
            correctlySelected.retainAll(correctOptions);

            double partialScore = ((double) correctlySelected.size() / correctOptions.size()) * getMaxScore(answer);
            answer.setScore(partialScore);
        }
    }
}
