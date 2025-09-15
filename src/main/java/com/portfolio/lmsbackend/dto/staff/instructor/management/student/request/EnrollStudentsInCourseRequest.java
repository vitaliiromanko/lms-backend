package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record EnrollStudentsInCourseRequest(
        @NotNull
        @JsonProperty("course_id")
        UUID courseId,
        @NotEmpty
        @JsonProperty("student_ids")
        Set<@NotNull UUID> studentIds
) {
}
