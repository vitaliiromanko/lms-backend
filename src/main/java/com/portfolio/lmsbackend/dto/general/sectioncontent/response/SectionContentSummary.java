package com.portfolio.lmsbackend.dto.general.sectioncontent.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.enums.content.SectionContentType;
import com.portfolio.lmsbackend.model.content.SectionContent;

import java.time.LocalDateTime;
import java.util.UUID;

public record SectionContentSummary(
        @JsonView(Views.Basic.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.Basic.class)
        @JsonProperty("type")
        SectionContentType type,
        @JsonView(Views.Basic.class)
        @JsonProperty("title")
        String title,
        @JsonView(Views.General.class)
        @JsonProperty("status")
        SectionContentStatus status,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonView(Views.Detailed.class)
        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    public SectionContentSummary(SectionContent content) {
        this(
                content.getId(),
                content.getType(),
                content.getTitle(),
                content.getStatus(),
                content.getCreatedAt(),
                content.getUpdatedAt()
        );
    }
}
