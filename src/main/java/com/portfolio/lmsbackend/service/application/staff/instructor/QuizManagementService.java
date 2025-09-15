package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request.CreateQuizRequest;

import java.util.UUID;

public interface QuizManagementService {
    void create(UUID userId, CreateQuizRequest createQuizRequest);
}
