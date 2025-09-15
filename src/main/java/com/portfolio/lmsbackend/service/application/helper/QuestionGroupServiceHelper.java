package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.QuestionGroupIsArchivedException;
import com.portfolio.lmsbackend.exception.course.QuestionGroupNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.question.QuestionGroup;
import com.portfolio.lmsbackend.repository.course.QuestionGroupRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus.ARCHIVED;

@Component
public class QuestionGroupServiceHelper
        extends BaseServiceHelper<QuestionGroup, QuestionGroupRepository, QuestionGroupNotFoundException> {
    protected QuestionGroupServiceHelper(QuestionGroupRepository repository) {
        super(repository, QuestionGroupNotFoundException::new);
    }

    public QuestionGroup findByIdAndNotArchivedOrThrow(UUID id) {
        QuestionGroup group = findByIdOrThrow(id);

        if (group.getStatus() == ARCHIVED) {
            throw new QuestionGroupIsArchivedException();
        }

        return group;
    }
}
