package com.portfolio.lmsbackend.dto.staff.admin.management.course.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.enums.course.CourseStatus;
import com.portfolio.lmsbackend.model.course.Course;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AdminCourseSummary {
    private final UUID id;
    private final String title;
    private final CourseStatus status;
    private final CreatedByResponse createdBy;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    protected AdminCourseSummary(Course course) {
        this(
                course.getId(),
                course.getTitle(),
                course.getStatus(),
                new CreatedByResponse(course.getCreatedBy()),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }

    @JsonCreator
    protected AdminCourseSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("status") CourseStatus status,
            @JsonProperty("created_by") CreatedByResponse createdBy,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.createdBy = createdBy;
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

    @JsonProperty("status")
    public CourseStatus status() {
        return status;
    }

    @JsonProperty("created_by")
    public CreatedByResponse createdBy() {
        return createdBy;
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
