package com.portfolio.lmsbackend.service.application.answer.impl;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.model.content.quiz.answer.MultipleChoiceAnswer;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.model.content.quiz.question.MultipleChoiceQuestion;
import com.portfolio.lmsbackend.service.application.answer.AutoGradingAnswerHandler;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.MULTIPLE_CHOICE;

@Service
public class MultipleChoiceAnswerHandler extends AutoGradingAnswerHandler {
    @Override
    public Set<QuestionType> getSupportedQuestionTypes() {
        return Set.of(MULTIPLE_CHOICE);
    }

    @Override
    protected void grade(Answer answer) {
        List<ChoiceOption> options = ((MultipleChoiceQuestion) answer.getQuizQuestion().getQuestion())
                .getOptions();
        Set<ChoiceOption> selectedOptions = ((MultipleChoiceAnswer) answer).getSelectedOptions();

        int totalOptions = options.size();
        int totalCorrect = getCorrectCount(options);

        int selectedCorrect = getCorrectCount(selectedOptions);
        int selectedIncorrect = selectedOptions.size() - selectedCorrect;

        double score = getMaxScore(answer) * calculateScoreCoefficient(totalOptions, totalCorrect,
                selectedCorrect, selectedIncorrect);
        answer.setScore(score);
    }

    private static int getCorrectCount(Collection<ChoiceOption> options) {
        return (int) options.stream()
                .filter(ChoiceOption::getCorrect)
                .count();
    }

    private static double calculateScoreCoefficient(int totalOptions, int totalCorrect,
                                                    int selectedCorrect, int selectedIncorrect) {
        double tpRatio = (double) selectedCorrect / totalCorrect;
        double fpRatio = (double) selectedIncorrect / totalOptions;
        return Math.max(0.0, tpRatio - fpRatio);
    }
}
