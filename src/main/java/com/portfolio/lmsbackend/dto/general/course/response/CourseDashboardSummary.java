package com.portfolio.lmsbackend.dto.general.course.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.model.course.Course;

import java.util.UUID;

public abstract class CourseDashboardSummary {
    @JsonView(Views.Basic.class)
    private final UUID id;

    @JsonView(Views.Basic.class)
    private final String title;

    protected CourseDashboardSummary(Course course) {
        this(
                course.getId(),
                course.getTitle()
        );
    }

    @JsonCreator
    protected CourseDashboardSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title
    ) {
        this.id = id;
        this.title = title;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }
}
