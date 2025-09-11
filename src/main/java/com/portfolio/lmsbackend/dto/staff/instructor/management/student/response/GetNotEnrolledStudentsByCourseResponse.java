package com.portfolio.lmsbackend.dto.staff.instructor.management.student.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.user.Student;

import java.util.UUID;

public class GetNotEnrolledStudentsByCourseResponse extends StudentSummary {
    public GetNotEnrolledStudentsByCourseResponse(Student student) {
        super(student);
    }

    @JsonCreator
    protected GetNotEnrolledStudentsByCourseResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("photo_url") String photoUrl
    ) {
        super(id, firstName, lastName, email, photoUrl);
    }
}
