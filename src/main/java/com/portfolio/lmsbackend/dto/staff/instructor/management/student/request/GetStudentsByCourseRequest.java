package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.SearchRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Sort;

public abstract class GetStudentsByCourseRequest extends SearchRequest {
    @NotBlank
    private final String courseId;

    @JsonCreator
    protected GetStudentsByCourseRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search,
            @JsonProperty("course_id") String courseId
    ) {
        super(pageNumber, pageSize, sortDirection, search);
        this.courseId = courseId;
    }

    @JsonProperty("course_id")
    public String courseId() {
        return courseId;
    }
}
