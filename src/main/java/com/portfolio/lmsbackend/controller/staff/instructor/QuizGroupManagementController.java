package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.request.UpdateQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response.GetQuizGroupDetailedResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuizGroupManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.controller.ControllerViewHelper.wrapResponseWithView;
import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;

@Tag(
        name = "Instructor / QuizGroupManagementController",
        description = "Endpoints for managing quiz groups"
)
@RestController
@RequestMapping("/quiz/group/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class QuizGroupManagementController {
    private final QuizGroupManagementService quizGroupManagementService;

    @Operation(
            summary = "Get quiz group information",
            description = "Endpoint to retrieve information about a quiz group."
    )
    @GetMapping("/{groupId}")
    public ResponseEntity<MappingJacksonValue> getOne(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID groupId
    ) {
        GetQuizGroupDetailedResponse response = quizGroupManagementService.getOne(groupId);
        return ResponseEntity.ok().body(wrapResponseWithView(response, authentication));
    }

    @Operation(
            summary = "Update quiz group",
            description = "Endpoint to update information about a quiz group."
    )
    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateQuizGroupRequest updateQuizGroupRequest
    ) {
        quizGroupManagementService.update(updateQuizGroupRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
