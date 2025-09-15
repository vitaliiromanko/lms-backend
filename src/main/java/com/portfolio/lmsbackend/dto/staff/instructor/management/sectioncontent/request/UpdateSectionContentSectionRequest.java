package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateSectionContentSectionRequest(
        @NotNull
        @JsonProperty("section_content_id")
        UUID sectionContentId,
        @NotNull
        @JsonProperty("new_section_id")
        UUID newSectionId
) {
}
