package com.portfolio.lmsbackend.dto.staff.admin.management.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAdminUserEmailVerifiedRequest(
        @NotBlank
        @JsonProperty("user_id")
        String userId,
        @NotNull
        @JsonProperty("new_email_verified")
        Boolean newEmailVerified
) {
}
