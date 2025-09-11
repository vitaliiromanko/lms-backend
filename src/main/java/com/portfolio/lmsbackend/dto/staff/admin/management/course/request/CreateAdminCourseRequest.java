package com.portfolio.lmsbackend.dto.staff.admin.management.course.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAdminCourseRequest(
        @NotBlank
        @JsonProperty("category_id")
        String categoryId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
