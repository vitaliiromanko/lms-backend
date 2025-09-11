package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.CreateQuestionRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionManagementService {
    void create(String userId, CreateQuestionRequest createQuestionRequest, List<MultipartFile> images);
}
