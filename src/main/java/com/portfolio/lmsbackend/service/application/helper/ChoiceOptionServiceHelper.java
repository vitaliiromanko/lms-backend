package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.ChoiceOptionNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.repository.course.ChoiceOptionRepository;
import org.springframework.stereotype.Component;

@Component
public class ChoiceOptionServiceHelper extends BaseServiceHelper<ChoiceOption, ChoiceOptionRepository, ChoiceOptionNotFoundException> {
    protected ChoiceOptionServiceHelper(ChoiceOptionRepository repository) {
        super(repository, ChoiceOptionNotFoundException::new);
    }
}
