package com.portfolio.lmsbackend.dto.staff.admin.management.category.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAdminCategoryRequest(
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
