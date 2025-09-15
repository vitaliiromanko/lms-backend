package com.portfolio.lmsbackend.dto.staff.instructor.management.section.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateSectionRequest(
        @NotNull
        @JsonProperty("course_id")
        UUID courseId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
