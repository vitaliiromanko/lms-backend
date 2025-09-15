package com.portfolio.lmsbackend.dto.staff.admin.management.course.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.course.CourseStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAdminCourseStatusRequest(
        @NotNull
        @JsonProperty("course_id")
        UUID courseId,
        @NotNull
        @JsonProperty("new_status")
        CourseStatus newStatus
) {
}
