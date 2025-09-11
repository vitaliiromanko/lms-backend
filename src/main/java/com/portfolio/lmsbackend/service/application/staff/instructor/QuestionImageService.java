package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.model.content.quiz.question.Question;
import com.portfolio.lmsbackend.model.media.image.QuestionImage;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionImageService {
    QuestionImage saveQuestionImage(MultipartFile image, Question question);
}
