package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateSectionContentSectionRequest(
        @NotBlank
        @JsonProperty("section_content_id")
        String sectionContentId,
        @NotBlank
        @JsonProperty("new_section_id")
        String newSectionId
) {
}
