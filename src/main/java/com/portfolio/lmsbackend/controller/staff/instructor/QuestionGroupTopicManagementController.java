package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.CreateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicParentRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupRootTopicResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupTopicResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionGroupTopicManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@RequestMapping("/question/group/topic/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class QuestionGroupTopicManagementController {
    private final QuestionGroupTopicManagementService questionGroupTopicManagementService;

    @PostMapping
    public ResponseEntity<String> create(
            @Valid @RequestBody CreateQuestionGroupTopicRequest createQuestionGroupTopicRequest
    ) {
        questionGroupTopicManagementService.create(createQuestionGroupTopicRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @GetMapping
    public ResponseEntity<GetQuestionGroupRootTopicResponse> getRoot() {
        return ResponseEntity.ok().body(questionGroupTopicManagementService.getRoot());
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<MappingJacksonValue> getOne(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID topicId
    ) {
        GetQuestionGroupTopicResponse response = questionGroupTopicManagementService.getOne(topicId);
        return ResponseEntity.ok().body(wrapResponseWithView(response, authentication));
    }

    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateQuestionGroupTopicRequest updateQuestionGroupTopicRequest
    ) {
        questionGroupTopicManagementService.update(updateQuestionGroupTopicRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/parent")
    public ResponseEntity<String> updateParent(
            @Valid @RequestBody UpdateQuestionGroupTopicParentRequest updateQuestionGroupTopicParentRequest
    ) {
        questionGroupTopicManagementService.updateParent(updateQuestionGroupTopicParentRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> delete(
            @PathVariable UUID topicId
    ) {
        questionGroupTopicManagementService.delete(topicId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
