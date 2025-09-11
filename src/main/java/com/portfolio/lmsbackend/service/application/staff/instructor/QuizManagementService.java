package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request.CreateQuizRequest;

public interface QuizManagementService {
    void create(String userId, CreateQuizRequest createQuizRequest);
}
