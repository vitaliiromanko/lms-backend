package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.CreateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicParentRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupRootTopicResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupTopicResponse;

import java.util.UUID;

public interface QuestionGroupTopicManagementService {
    void create(CreateQuestionGroupTopicRequest createQuestionGroupTopicRequest);

    GetQuestionGroupRootTopicResponse getRoot();

    GetQuestionGroupTopicResponse getOne(UUID topicId);

    void update(UpdateQuestionGroupTopicRequest updateQuestionGroupTopicRequest);

    void updateParent(UpdateQuestionGroupTopicParentRequest updateQuestionGroupTopicParentRequest);

    void delete(UUID topicId);
}
