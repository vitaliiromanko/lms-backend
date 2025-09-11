package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.request.UpdateQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response.GetQuizGroupResponse;

public interface QuizGroupManagementService {
    GetQuizGroupResponse getOne(String groupId);

    void update(UpdateQuizGroupRequest updateQuizGroupRequest);
}
