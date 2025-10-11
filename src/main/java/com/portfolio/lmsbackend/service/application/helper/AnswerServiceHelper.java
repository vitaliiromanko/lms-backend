package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.AnswerNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.repository.course.AnswerRepository;
import org.springframework.stereotype.Component;

@Component
public class AnswerServiceHelper extends BaseServiceHelper<Answer, AnswerRepository, AnswerNotFoundException> {
    protected AnswerServiceHelper(AnswerRepository repository) {
        super(repository, AnswerNotFoundException::new);
    }
}
