package com.portfolio.lmsbackend.service.application.answer.impl;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.model.content.quiz.answer.FillTheGapsAnswer;
import com.portfolio.lmsbackend.model.content.quiz.answer.GapAnswerSegment;
import com.portfolio.lmsbackend.model.content.quiz.question.FillTheGapsQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.MissingTextSegment;
import com.portfolio.lmsbackend.service.application.answer.AutoGradingAnswerHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.FILL_THE_GAPS;

@Service
public class FillTheGapsAnswerHandler extends AutoGradingAnswerHandler {
    @Override
    public Set<QuestionType> getSupportedQuestionTypes() {
        return Set.of(FILL_THE_GAPS);
    }

    @Override
    protected double grade(Answer answer) {
        List<MissingTextSegment> missingTextSegments = ((FillTheGapsQuestion) answer.getQuizQuestion().getQuestion())
                .getMissingTextSegments();
        Set<GapAnswerSegment> gapAnswerSegments = ((FillTheGapsAnswer) answer).getGapAnswerSegments();

        int correctCount = 0;
        int total = missingTextSegments.size();

        for (GapAnswerSegment gapAnswerSegment : gapAnswerSegments) {
            String enteredText = gapAnswerSegment.getText();
            String correctText = gapAnswerSegment.getMissingTextSegment().getText();

            if (enteredText != null && enteredText.trim().equalsIgnoreCase(correctText.trim())) {
                correctCount++;
            }
        }

        return ((double) correctCount / total) * getMaxScore(answer);
    }
}
