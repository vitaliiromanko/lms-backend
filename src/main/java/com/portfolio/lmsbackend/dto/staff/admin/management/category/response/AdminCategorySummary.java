package com.portfolio.lmsbackend.dto.staff.admin.management.category.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.course.Category;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AdminCategorySummary {
    private final UUID id;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    protected AdminCategorySummary(Category category) {
        this(category.getId(), category.getTitle(), category.getCreatedAt(), category.getUpdatedAt());
    }

    @JsonCreator
    protected AdminCategorySummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("title")
    public String title() {
        return title;
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
