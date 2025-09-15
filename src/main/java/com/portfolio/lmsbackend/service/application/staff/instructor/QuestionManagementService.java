package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.CreateQuestionRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface QuestionManagementService {
    void create(UUID userId, CreateQuestionRequest createQuestionRequest, List<MultipartFile> images);
}
