package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.CreateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicParentRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupRootTopicResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupTopicResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionGroupTopicManagementService;
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
import static org.springframework.http.HttpStatus.CREATED;

@Tag(
        name = "Instructor / QuestionGroupTopicManagementController",
        description = "Endpoints for managing question group topics"
)
@RestController
@RequestMapping("/question/group/topic/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class QuestionGroupTopicManagementController {
    private final QuestionGroupTopicManagementService questionGroupTopicManagementService;

    @Operation(
            summary = "Create topic",
            description = "Endpoint to create a new question group topic."
    )
    @PostMapping
    public ResponseEntity<String> create(
            @Valid @RequestBody CreateQuestionGroupTopicRequest createQuestionGroupTopicRequest
    ) {
        questionGroupTopicManagementService.create(createQuestionGroupTopicRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Get root topic information",
            description = "Endpoint to retrieve information about a root question group topic."
    )
    @GetMapping
    public ResponseEntity<GetQuestionGroupRootTopicResponse> getRoot() {
        return ResponseEntity.ok().body(questionGroupTopicManagementService.getRoot());
    }

    @Operation(
            summary = "Get topic information",
            description = "Endpoint to retrieve information about a question group topic."
    )
    @GetMapping("/{topicId}")
    public ResponseEntity<MappingJacksonValue> getOne(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID topicId
    ) {
        GetQuestionGroupTopicResponse response = questionGroupTopicManagementService.getOne(topicId);
        return ResponseEntity.ok().body(wrapResponseWithView(response, authentication));
    }

    @Operation(
            summary = "Update topic",
            description = "Endpoint to update information about a question group topic."
    )
    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateQuestionGroupTopicRequest updateQuestionGroupTopicRequest
    ) {
        questionGroupTopicManagementService.update(updateQuestionGroupTopicRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Update topic parent",
            description = "Endpoint to update the parent of a question group topic."
    )
    @PutMapping("/parent")
    public ResponseEntity<String> updateParent(
            @Valid @RequestBody UpdateQuestionGroupTopicParentRequest updateQuestionGroupTopicParentRequest
    ) {
        questionGroupTopicManagementService.updateParent(updateQuestionGroupTopicParentRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Delete topic",
            description = "Endpoint to delete a question group topic."
    )
    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> delete(
            @PathVariable UUID topicId
    ) {
        questionGroupTopicManagementService.delete(topicId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
