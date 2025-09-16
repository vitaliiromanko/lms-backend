package com.portfolio.lmsbackend.dto.general.course.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.course.CourseStatus;
import com.portfolio.lmsbackend.model.course.Course;

import java.time.LocalDateTime;
import java.util.UUID;

public class CourseStaffDashboardSummary extends CourseDashboardSummary {
    @JsonView(Views.General.class)
    private final CourseStatus status;

    @JsonView(Views.Detailed.class)
    private final CreatedByResponse createdBy;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime createdAt;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime updatedAt;

    public CourseStaffDashboardSummary(Course course) {
        super(course);
        this.status = course.getStatus();
        this.createdBy = new CreatedByResponse(course.getCreatedBy());
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
    }

    @JsonCreator
    protected CourseStaffDashboardSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("status") CourseStatus status,
            @JsonProperty("created_by") CreatedByResponse createdBy,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, title);
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
