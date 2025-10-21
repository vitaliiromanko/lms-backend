package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.AttemptNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import org.springframework.stereotype.Component;

@Component
public class AttemptServiceHelper extends BaseServiceHelper<Attempt, AttemptRepository, AttemptNotFoundException> {
    protected AttemptServiceHelper(AttemptRepository repository) {
        super(repository, AttemptNotFoundException::new);
    }

    public Integer getAttemptNumber(Attempt attempt) {
        return (int) repository.countAttemptsUpTo(
                attempt.getUser(),
                attempt.getQuiz().getGroup(),
                attempt.getCreatedAt()
        );
    }
}
