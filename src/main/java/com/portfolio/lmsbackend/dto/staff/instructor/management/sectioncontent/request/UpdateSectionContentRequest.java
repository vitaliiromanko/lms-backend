package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateSectionContentRequest(
        @NotNull
        @JsonProperty("section_content_id")
        UUID sectionContentId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
