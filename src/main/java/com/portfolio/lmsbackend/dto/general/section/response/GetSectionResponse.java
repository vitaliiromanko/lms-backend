package com.portfolio.lmsbackend.dto.general.section.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.general.sectioncontent.response.SectionContentSummary;
import com.portfolio.lmsbackend.enums.course.SectionStatus;
import com.portfolio.lmsbackend.model.content.SectionContent;
import com.portfolio.lmsbackend.model.course.Section;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetSectionResponse(
        @JsonView(Views.Basic.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.Basic.class)
        @JsonProperty("title")
        String title,
        @JsonView(Views.Basic.class)
        @JsonProperty("contents")
        List<SectionContentSummary> contents,
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
    public GetSectionResponse(Section section, List<SectionContent> contents) {
        this(
                section.getId(),
                section.getTitle(),
                contents.stream()
                        .map(SectionContentSummary::new)
                        .toList(),
                section.getStatus(),
                section.getCreatedAt(),
                section.getUpdatedAt()
        );
    }
}
