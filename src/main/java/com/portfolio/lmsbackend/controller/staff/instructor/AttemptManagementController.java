package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.FinalizeAttemptGradingRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.GetFinishedAttemptsByQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.GradeFinishedAttemptAnswerRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response.GetFinishedAttemptResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response.GetFinishedAttemptsByQuizGroupResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.AttemptManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;

@Tag(
        name = "Instructor / AttemptManagementController",
        description = "Endpoints for managing quiz attempts"
)
@RestController
@RequestMapping("/attempt/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class AttemptManagementController {
    private final AttemptManagementService attemptManagementService;

    @Operation(
            summary = "Get finished attempt summaries",
            description = "Endpoint to retrieve a page of brief information about finished attempts by quiz group."
    )
    @GetMapping
    public ResponseEntity<Page<GetFinishedAttemptsByQuizGroupResponse>> getFinishedAttemptsByQuizGroup(
            @Valid @RequestBody GetFinishedAttemptsByQuizGroupRequest getFinishedAttemptsByQuizGroupRequest
    ) {
        return ResponseEntity.ok().body(attemptManagementService
                .getFinishedAttemptsByQuizGroup(getFinishedAttemptsByQuizGroupRequest));
    }

    @Operation(
            summary = "Get finished attempt information",
            description = "Endpoint to retrieve information about a finished attempt."
    )
    @GetMapping("/{attemptId}")
    public ResponseEntity<GetFinishedAttemptResponse> getFinishedAttempt(
            @PathVariable UUID attemptId
    ) {
        return ResponseEntity.ok().body(attemptManagementService.getFinishedAttempt(attemptId));
    }

    @Operation(
            summary = "Grade finished attempt answer",
            description = "Endpoint to assign a grade to an answer in a finished attempt."
    )
    @PutMapping("/answer")
    public ResponseEntity<String> gradeFinishedAttemptAnswer(
            @Valid @RequestBody GradeFinishedAttemptAnswerRequest gradeFinishedAttemptAnswerRequest
    ) {
        attemptManagementService.gradeFinishedAttemptAnswer(gradeFinishedAttemptAnswerRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Finalize attempt grading",
            description = "Endpoint to finalize the grading of an attempt."
    )
    @PutMapping("/finalize-grading")
    public ResponseEntity<String> finalizeAttemptGrading(
            @Valid @RequestBody FinalizeAttemptGradingRequest finalizeAttemptGradingRequest
    ) {
        attemptManagementService.finalizeAttemptGrading(finalizeAttemptGradingRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
