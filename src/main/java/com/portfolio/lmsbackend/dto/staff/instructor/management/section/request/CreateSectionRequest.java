package com.portfolio.lmsbackend.dto.staff.instructor.management.section.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSectionRequest(
        @NotBlank
        @JsonProperty("course_id")
        String courseId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
