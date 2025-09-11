package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.QuizGroupNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.repository.course.QuizGroupRepository;
import org.springframework.stereotype.Component;

@Component
public class QuizGroupServiceHelper extends BaseServiceHelper<QuizGroup, QuizGroupRepository, QuizGroupNotFoundException> {
    protected QuizGroupServiceHelper(QuizGroupRepository repository) {
        super(repository, QuizGroupNotFoundException::new);
    }
}
