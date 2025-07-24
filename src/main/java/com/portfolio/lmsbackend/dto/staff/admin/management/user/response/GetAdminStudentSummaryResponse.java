package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.model.user.Student;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetAdminStudentSummaryResponse extends GetAdminUserSummaryResponse {
    public GetAdminStudentSummaryResponse(Student student) {
        super(student);
    }

    @JsonCreator
    protected GetAdminStudentSummaryResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") UserType type,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("photo_url") String photoUrl,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, type, firstName, lastName, email, emailVerified, photoUrl, status, createdAt, updatedAt);
    }
}
