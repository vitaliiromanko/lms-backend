package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSectionContentPositionRequest(
        @NotBlank
        @JsonProperty("section_content_id")
        String sectionContentId,
        @NotNull
        @Min(0)
        @JsonProperty("new_position")
        Integer newPosition
) {
}
