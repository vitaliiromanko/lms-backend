package com.portfolio.lmsbackend.dto.staff.admin.management.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAdminUserEmailVerifiedRequest(
        @NotNull
        @JsonProperty("user_id")
        UUID userId,
        @NotNull
        @JsonProperty("new_email_verified")
        Boolean newEmailVerified
) {
}
