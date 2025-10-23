package com.portfolio.lmsbackend.controller.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.CreateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseStatusRequest;
import com.portfolio.lmsbackend.service.application.staff.admin.CourseManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(
        name = "Administrator / CourseManagementController",
        description = "Endpoints for managing courses"
)
@RestController
@RequestMapping("/course/manage")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CourseManagementController {
    private final CourseManagementService courseManagementService;

    @Operation(
            summary = "Create course",
            description = "Endpoint to create a new course."
    )
    @PostMapping
    public ResponseEntity<String> create(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody CreateAdminCourseRequest createAdminCourseRequest
    ) {
        courseManagementService.create(UUID.fromString(userId), createAdminCourseRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Update course",
            description = "Endpoint to update information about a course."
    )
    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateAdminCourseRequest updateAdminCourseRequest
    ) {
        courseManagementService.update(updateAdminCourseRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Update course status",
            description = "Endpoint to update course status."
    )
    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(
            @Valid @RequestBody UpdateAdminCourseStatusRequest updateAdminCourseStatusRequest
    ) {
        courseManagementService.updateStatus(updateAdminCourseStatusRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Delete course",
            description = "Endpoint to delete a course."
    )
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> delete(
            @PathVariable UUID courseId
    ) {
        courseManagementService.delete(courseId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
