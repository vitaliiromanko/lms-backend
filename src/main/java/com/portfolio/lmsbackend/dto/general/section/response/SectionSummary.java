package com.portfolio.lmsbackend.dto.general.section.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.course.SectionStatus;
import com.portfolio.lmsbackend.model.course.Section;

import java.time.LocalDateTime;
import java.util.UUID;

public record SectionSummary(
        @JsonView(Views.Basic.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.Basic.class)
        @JsonProperty("title")
        String title,
        @JsonView(Views.Basic.class)
        @JsonProperty("content_count")
        Integer contentCount,
        @JsonView(Views.General.class)
        @JsonProperty("status")
        SectionStatus status,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonView(Views.Detailed.class)
        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    public SectionSummary(Section section) {
        this(
                section.getId(),
                section.getTitle(),
                section.getContents() == null ? 0 : section.getContents().size(),
                section.getStatus(),
                section.getCreatedAt(),
                section.getUpdatedAt()
        );
    }
}
