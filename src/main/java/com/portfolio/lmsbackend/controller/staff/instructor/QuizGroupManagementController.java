package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.request.UpdateQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response.GetQuizGroupResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuizGroupManagementService;
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
@RequestMapping("/quiz/group/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class QuizGroupManagementController {
    private final QuizGroupManagementService quizGroupManagementService;

    @GetMapping("/{groupId}")
    public ResponseEntity<MappingJacksonValue> getOne(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable String groupId
    ) {
        GetQuizGroupResponse response = quizGroupManagementService.getOne(groupId);
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
            @Valid @RequestBody UpdateQuizGroupRequest updateQuizGroupRequest
    ) {
        quizGroupManagementService.update(updateQuizGroupRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
