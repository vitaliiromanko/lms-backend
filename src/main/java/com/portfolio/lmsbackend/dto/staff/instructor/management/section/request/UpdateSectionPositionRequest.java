package com.portfolio.lmsbackend.dto.staff.instructor.management.section.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateSectionPositionRequest(
        @NotNull
        @JsonProperty("section_id")
        UUID sectionId,
        @NotNull
        @Min(0)
        @JsonProperty("new_position")
        Integer newPosition
) {
}
