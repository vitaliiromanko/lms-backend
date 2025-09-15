package com.portfolio.lmsbackend.dto.staff.instructor.management.section.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.course.SectionStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateSectionStatusRequest(
        @NotNull
        @JsonProperty("section_id")
        UUID sectionId,
        @NotNull
        @JsonProperty("new_status")
        SectionStatus newStatus
) {
}
