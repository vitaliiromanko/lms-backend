package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.QuestionGroupTopicNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.UserQuestionGroupTopic;
import com.portfolio.lmsbackend.repository.course.UserQuestionGroupTopicRepository;
import org.springframework.stereotype.Component;

@Component
public class UserQuestionGroupTopicServiceHelper extends
        BaseServiceHelper<UserQuestionGroupTopic, UserQuestionGroupTopicRepository, QuestionGroupTopicNotFoundException> {
    protected UserQuestionGroupTopicServiceHelper(UserQuestionGroupTopicRepository repository) {
        super(repository, QuestionGroupTopicNotFoundException::new);
    }
}
