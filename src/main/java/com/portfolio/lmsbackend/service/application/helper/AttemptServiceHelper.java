package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.AttemptNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import org.springframework.stereotype.Component;

@Component
public class AttemptServiceHelper extends BaseServiceHelper<Attempt, AttemptRepository, AttemptNotFoundException> {
    protected AttemptServiceHelper(AttemptRepository repository) {
        super(repository, AttemptNotFoundException::new);
    }

    public static Double calculateScorePct(Attempt attempt) {
        double totalScore = attempt.getAnswers().stream()
                .mapToDouble(Answer::getScore)
                .sum();

        double maxScore = attempt.getAnswers().stream()
                .mapToDouble(a -> a.getQuizQuestion().getMaxScore())
                .sum();

        return maxScore == 0.0 ? 100.0 : (totalScore / maxScore) * 100;
    }
}
