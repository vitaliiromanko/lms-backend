package com.portfolio.lmsbackend.dto.staff.admin.management.category.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.course.Category;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetAdminCategorySummaryResponse extends AdminCategorySummary {
    private final Integer courseCount;

    public GetAdminCategorySummaryResponse(Category category) {
        super(category);
        this.courseCount = category.getCourses() == null ? 0 : category.getCourses().size();
    }

    @JsonCreator
    protected GetAdminCategorySummaryResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("course_count") Integer courseCount,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, title, createdAt, updatedAt);
        this.courseCount = courseCount;
    }

    @JsonProperty("course_count")
    public Integer courseCount() {
        return courseCount;
    }
}
