package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSectionContentStatusRequest(
        @NotBlank
        @JsonProperty("section_content_id")
        String sectionContentId,
        @NotNull
        @JsonProperty("new_status")
        SectionContentStatus newStatus
) {
}
