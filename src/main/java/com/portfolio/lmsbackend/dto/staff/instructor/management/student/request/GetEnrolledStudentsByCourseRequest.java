package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.EnrolledStudentSortField;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public class GetEnrolledStudentsByCourseRequest extends GetStudentsByCourseRequest {
    private final EnrolledStudentSortField sortField;

    @JsonCreator
    public GetEnrolledStudentsByCourseRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_field") EnrolledStudentSortField sortField,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search,
            @JsonProperty("course_id") UUID courseId
    ) {
        super(pageNumber, pageSize, sortDirection, search, courseId);
        this.sortField = sortField != null ? sortField : EnrolledStudentSortField.ENROLLED_AT;
    }

    @JsonProperty("sort_field")
    public EnrolledStudentSortField sortField() {
        return sortField;
    }
}
