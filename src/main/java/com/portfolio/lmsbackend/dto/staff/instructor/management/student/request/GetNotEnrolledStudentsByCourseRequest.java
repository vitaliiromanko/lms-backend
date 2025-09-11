package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.NotEnrolledStudentSortField;
import org.springframework.data.domain.Sort;

public class GetNotEnrolledStudentsByCourseRequest extends GetStudentsByCourseRequest {
    private final NotEnrolledStudentSortField sortField;

    @JsonCreator
    public GetNotEnrolledStudentsByCourseRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_field") NotEnrolledStudentSortField sortField,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search,
            @JsonProperty("course_id") String courseId
    ) {
        super(pageNumber, pageSize, sortDirection, search, courseId);
        this.sortField = sortField != null ? sortField : NotEnrolledStudentSortField.EMAIL;
    }

    @JsonProperty("sort_field")
    public NotEnrolledStudentSortField sortField() {
        return sortField;
    }
}
