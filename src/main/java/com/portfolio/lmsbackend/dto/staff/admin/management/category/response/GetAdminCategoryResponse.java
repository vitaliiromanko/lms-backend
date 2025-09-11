package com.portfolio.lmsbackend.dto.staff.admin.management.category.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.course.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GetAdminCategoryResponse extends AdminCategory {
    public GetAdminCategoryResponse(Category category) {
        super(category);
    }

    @JsonCreator
    protected GetAdminCategoryResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("courses") List<AdminCategoryCourseSummary> courses,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, title, courses, createdAt, updatedAt);
    }
}
