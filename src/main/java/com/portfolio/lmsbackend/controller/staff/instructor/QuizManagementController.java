package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request.CreateQuizRequest;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuizManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/quiz/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class QuizManagementController {
    private final QuizManagementService quizManagementService;

    @PostMapping
    public ResponseEntity<String> create(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody CreateQuizRequest createQuizRequest
    ) {
        quizManagementService.create(userId, createQuizRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }
}
