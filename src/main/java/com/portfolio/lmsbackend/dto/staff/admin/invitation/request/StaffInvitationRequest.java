package com.portfolio.lmsbackend.dto.staff.admin.invitation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StaffInvitationRequest(
        @NotBlank
        @Size(max = 100)
        @JsonProperty("first_name")
        String firstName,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("last_name")
        String lastName,
        @NotBlank
        @Size(max = 255)
        @Email
        @JsonProperty("email")
        String email,
        @NotNull
        @JsonProperty("role")
        StaffRole role
) {
}
