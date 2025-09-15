package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateSectionContentPositionRequest(
        @NotNull
        @JsonProperty("section_content_id")
        UUID sectionContentId,
        @NotNull
        @Min(0)
        @JsonProperty("new_position")
        Integer newPosition
) {
}
