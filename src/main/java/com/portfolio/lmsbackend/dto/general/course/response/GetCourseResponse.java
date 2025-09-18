package com.portfolio.lmsbackend.dto.general.course.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.general.section.response.SectionSummary;
import com.portfolio.lmsbackend.enums.course.CourseStatus;
import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.model.course.Section;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.portfolio.lmsbackend.enums.course.SectionStatus.VISIBLE;

public record GetCourseResponse(
        @JsonView(Views.Basic.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.Basic.class)
        @JsonProperty("title")
        String title,
        @JsonView(Views.Basic.class)
        @JsonProperty("sections")
        List<SectionSummary> sections,
        @JsonView(Views.General.class)
        @JsonProperty("status")
        CourseStatus status,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_by")
        CreatedByResponse createdBy,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonView(Views.Detailed.class)
        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    public GetCourseResponse(Course course, boolean includeInvisible) {
        this(
                course.getId(),
                course.getTitle(),
                buildSectionSummaries(course, includeInvisible),
                course.getStatus(),
                new CreatedByResponse(course.getCreatedBy()),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }

    private static List<SectionSummary> buildSectionSummaries(Course course, boolean includeInvisible) {
        Stream<Section> sectionsStream = course.getSections().stream();

        if (!includeInvisible) {
            sectionsStream = sectionsStream.filter(s -> s.getStatus() == VISIBLE);
        }

        return sectionsStream
                .map(s -> new SectionSummary(s, includeInvisible))
                .toList();
    }
}
