package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.model.content.quiz.question.Question;
import com.portfolio.lmsbackend.model.media.image.QuestionImage;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionImageService;
import com.portfolio.lmsbackend.service.infrastructure.media.MediaStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionImageServiceImpl implements QuestionImageService {
    private final MediaStorageService mediaStorageService;

    private static final String QUESTION_IMAGE_FOLDER_PATTERN = "question/%s/images";

    @Override
    public QuestionImage saveQuestionImage(MultipartFile image, Question question) {
        String questionImageFolder = QUESTION_IMAGE_FOLDER_PATTERN.formatted(question.getId().toString());
        Map<String, Object> uploadResult = mediaStorageService.upload(image, questionImageFolder, null);
        return new QuestionImage(
                image.getOriginalFilename(),
                (String) uploadResult.get("secure_url"),
                (String) uploadResult.get("public_id"),
                question
        );
    }
}
