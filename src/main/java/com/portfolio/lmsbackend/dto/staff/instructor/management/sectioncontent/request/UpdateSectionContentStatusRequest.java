package com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateSectionContentStatusRequest(
        @NotNull
        @JsonProperty("section_content_id")
        UUID sectionContentId,
        @NotNull
        @JsonProperty("new_status")
        SectionContentStatus newStatus
) {
}
