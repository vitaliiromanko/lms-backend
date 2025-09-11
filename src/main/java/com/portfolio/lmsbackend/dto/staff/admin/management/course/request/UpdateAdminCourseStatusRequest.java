package com.portfolio.lmsbackend.dto.staff.admin.management.course.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.course.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAdminCourseStatusRequest(
        @NotBlank
        @JsonProperty("course_id")
        String courseId,
        @NotNull
        @JsonProperty("new_status")
        CourseStatus newStatus
) {
}
