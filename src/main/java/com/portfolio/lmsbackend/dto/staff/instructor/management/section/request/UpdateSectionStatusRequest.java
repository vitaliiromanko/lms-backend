package com.portfolio.lmsbackend.dto.staff.instructor.management.section.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.course.SectionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSectionStatusRequest(
        @NotBlank
        @JsonProperty("section_id")
        String sectionId,
        @NotNull
        @JsonProperty("new_status")
        SectionStatus newStatus
) {
}
