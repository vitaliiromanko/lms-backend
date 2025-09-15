package com.portfolio.lmsbackend.dto.staff.admin.management.course.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateAdminCourseRequest(
        @NotNull
        @JsonProperty("category_id")
        UUID categoryId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
