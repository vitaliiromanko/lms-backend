package com.portfolio.lmsbackend.dto.general.sectioncontent.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.enums.content.SectionContentType;
import com.portfolio.lmsbackend.model.content.SectionContent;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class GetSectionContentResponse {
    @JsonView(Views.Basic.class)
    private final UUID id;

    @JsonView(Views.Basic.class)
    private final SectionContentType type;

    @JsonView(Views.Basic.class)
    private final String title;

    @JsonView(Views.General.class)
    private final SectionContentStatus status;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime createdAt;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime updatedAt;

    protected GetSectionContentResponse(SectionContent content) {
        this(
                content.getId(),
                content.getType(),
                content.getTitle(),
                content.getStatus(),
                content.getCreatedAt(),
                content.getUpdatedAt()
        );
    }

    @JsonCreator
    protected GetSectionContentResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") SectionContentType type,
            @JsonProperty("title") String title,
            @JsonProperty("status") SectionContentStatus status,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("type")
    public SectionContentType type() {
        return type;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }

    @JsonProperty("status")
    public SectionContentStatus status() {
        return status;
    }

    @JsonProperty("created_at")
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @JsonProperty("updated_at")
    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
