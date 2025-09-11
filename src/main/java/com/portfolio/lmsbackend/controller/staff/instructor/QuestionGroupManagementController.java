package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request.UpdateQuestionGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request.UpdateQuestionGroupStatusRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response.GetQuestionGroupResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionGroupManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/question/group/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class QuestionGroupManagementController {
    private final QuestionGroupManagementService questionGroupManagementService;

    @GetMapping("/{groupId}")
    public ResponseEntity<MappingJacksonValue> getOne(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable String groupId
    ) {
        GetQuestionGroupResponse response = questionGroupManagementService.getOne(groupId);
        MappingJacksonValue wrapper = new MappingJacksonValue(response);

        wrapper.setSerializationView(
                authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINISTRATOR"))
                        ? Views.Detailed.class
                        : Views.General.class
        );

        return ResponseEntity.ok().body(wrapper);
    }

    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateQuestionGroupRequest updateQuestionGroupRequest
    ) {
        questionGroupManagementService.update(updateQuestionGroupRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(
            @Valid @RequestBody UpdateQuestionGroupStatusRequest updateQuestionGroupStatusRequest
    ) {
        questionGroupManagementService.updateStatus(updateQuestionGroupStatusRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
