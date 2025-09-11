package com.portfolio.lmsbackend.controller.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.CreateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseStatusRequest;
import com.portfolio.lmsbackend.service.application.staff.admin.CourseManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/course/manage")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CourseManagementController {
    private final CourseManagementService courseManagementService;

    @PostMapping
    public ResponseEntity<String> create(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody CreateAdminCourseRequest createAdminCourseRequest
    ) {
        courseManagementService.create(userId, createAdminCourseRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateAdminCourseRequest updateAdminCourseRequest
    ) {
        courseManagementService.update(updateAdminCourseRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(
            @Valid @RequestBody UpdateAdminCourseStatusRequest updateAdminCourseStatusRequest
    ) {
        courseManagementService.updateStatus(updateAdminCourseStatusRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> delete(
            @PathVariable String courseId
    ) {
        courseManagementService.delete(courseId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
