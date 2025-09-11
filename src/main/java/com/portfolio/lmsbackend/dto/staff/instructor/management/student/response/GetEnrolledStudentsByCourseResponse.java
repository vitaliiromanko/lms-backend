package com.portfolio.lmsbackend.dto.staff.instructor.management.student.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.course.coursestudent.CourseStudent;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetEnrolledStudentsByCourseResponse extends StudentSummary {
    private final LocalDateTime enrolledAt;

    public GetEnrolledStudentsByCourseResponse(CourseStudent courseStudent) {
        super(courseStudent.getStudent());
        this.enrolledAt = courseStudent.getEnrolledAt();
    }

    @JsonCreator
    protected GetEnrolledStudentsByCourseResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("photo_url") String photoUrl,
            @JsonProperty("enrolled_at") LocalDateTime enrolledAt
    ) {
        super(id, firstName, lastName, email, photoUrl);
        this.enrolledAt = enrolledAt;
    }

    @JsonProperty("enrolled_at")
    public LocalDateTime enrolledAt() {
        return enrolledAt;
    }
}
