package com.portfolio.lmsbackend.dto.general.profile.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.model.user.Staff;

import java.util.UUID;

public record GetStaffProfileResponse(
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
        @JsonProperty("role")
        StaffRole role,
        @JsonProperty("status")
        UserStatus status
) {
    public GetStaffProfileResponse(Staff staff) {
        this(
                staff.getId(),
                staff.getFirstName(),
                staff.getLastName(),
                staff.getEmail(),
                staff.isEmailVerified(),
                staff.getPhoto() != null ? staff.getPhoto().getUrl() : null,
                staff.getRole(),
                staff.getStatus()
        );
    }
}
