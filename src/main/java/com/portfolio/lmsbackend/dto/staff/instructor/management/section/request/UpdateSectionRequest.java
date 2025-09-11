package com.portfolio.lmsbackend.dto.staff.instructor.management.section.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateSectionRequest(
        @NotBlank
        @JsonProperty("section_id")
        String sectionId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
