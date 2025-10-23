package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.CreateQuestionRequest;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionManagementService;
import com.portfolio.lmsbackend.validation.annotation.ValidImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(
        name = "Instructor / QuestionManagementController",
        description = "Endpoints for managing questions"
)
@Validated
@RestController
@RequestMapping("/question/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class QuestionManagementController {
    private final QuestionManagementService questionManagementService;

    @Operation(
            summary = "Create question",
            description = "Endpoint to create a new question."
    )
    @PostMapping
    public ResponseEntity<String> create(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestPart(name = "data") CreateQuestionRequest createQuestionRequest,
            @Valid @RequestPart(name = "images", required = false) List<@ValidImage MultipartFile> images
    ) {
        questionManagementService.create(UUID.fromString(userId), createQuestionRequest, images);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }
}
