package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.MissingTextSegmentNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.question.MissingTextSegment;
import com.portfolio.lmsbackend.repository.course.MissingTextSegmentRepository;
import org.springframework.stereotype.Component;

@Component
public class MissingTextSegmentServiceHelper
        extends BaseServiceHelper<MissingTextSegment, MissingTextSegmentRepository, MissingTextSegmentNotFoundException> {
    protected MissingTextSegmentServiceHelper(MissingTextSegmentRepository repository) {
        super(repository, MissingTextSegmentNotFoundException::new);
    }
}
