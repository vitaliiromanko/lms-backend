package com.portfolio.lmsbackend.dto.staff.instructor.management.section.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSectionPositionRequest(
        @NotBlank
        @JsonProperty("section_id")
        String sectionId,
        @NotNull
        @Min(0)
        @JsonProperty("new_position")
        Integer newPosition
) {
}
