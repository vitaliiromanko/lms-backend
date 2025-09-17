package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.request.UpdateQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response.GetQuizGroupDetailedResponse;

import java.util.UUID;

public interface QuizGroupManagementService {
    GetQuizGroupDetailedResponse getOne(UUID groupId);

    void update(UpdateQuizGroupRequest updateQuizGroupRequest);
}
