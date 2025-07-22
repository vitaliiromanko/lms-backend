package com.portfolio.lmsbackend.dto.general.profile.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.model.user.Student;

import java.util.UUID;

public record GetStudentProfileResponse(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email")
        String email,
        @JsonProperty("email_verified")
        boolean emailVerified,
        @JsonProperty("photo_url")
        String photoUrl,
        @JsonProperty("status")
        UserStatus status
) {
    public GetStudentProfileResponse(Student student) {
        this(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.isEmailVerified(),
                student.getPhoto() != null ? student.getPhoto().getUrl() : null,
                student.getStatus()
        );
    }
}
