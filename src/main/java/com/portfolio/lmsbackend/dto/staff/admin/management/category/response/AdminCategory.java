package com.portfolio.lmsbackend.dto.staff.admin.management.category.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.course.Category;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AdminCategory extends AdminCategorySummary {
    private final List<AdminCategoryCourseSummary> courses;

    protected AdminCategory(Category category) {
        super(category);
        this.courses = category.getCourses() != null ?
                category.getCourses().stream()
                        .map(AdminCategoryCourseSummary::new)
                        .sorted(Comparator.comparing(c -> c.title().toLowerCase()))
                        .collect(Collectors.toList()) :
                null;
    }

    @JsonCreator
    protected AdminCategory(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("courses") List<AdminCategoryCourseSummary> courses,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, title, createdAt, updatedAt);
        this.courses = courses;
    }

    @JsonProperty("courses")
    public List<AdminCategoryCourseSummary> courses() {
        return courses;
    }
}
