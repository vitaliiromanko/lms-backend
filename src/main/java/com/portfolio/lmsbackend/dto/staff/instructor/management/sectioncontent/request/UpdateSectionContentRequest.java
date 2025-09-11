package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateSectionContentRequest(
        @NotBlank
        @JsonProperty("section_content_id")
        String sectionContentId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
