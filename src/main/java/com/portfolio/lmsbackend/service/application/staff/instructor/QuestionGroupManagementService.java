package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request.UpdateQuestionGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request.UpdateQuestionGroupStatusRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response.GetQuestionGroupResponse;

import java.util.UUID;

public interface QuestionGroupManagementService {
    GetQuestionGroupResponse getOne(UUID groupId);

    void update(UpdateQuestionGroupRequest updateQuestionGroupRequest);

    void updateStatus(UpdateQuestionGroupStatusRequest updateQuestionGroupStatusRequest);
}
