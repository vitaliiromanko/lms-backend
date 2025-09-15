package com.portfolio.lmsbackend.dto.staff.admin.management.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAdminUserStatusRequest(
        @NotNull
        @JsonProperty("user_id")
        UUID userId,
        @NotNull
        @JsonProperty("new_status")
        UserStatus newStatus
) {
}
