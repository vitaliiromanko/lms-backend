package com.portfolio.lmsbackend.dto.staff.admin.management.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAdminUserStatusRequest(
        @NotBlank
        @JsonProperty("user_id")
        String userId,
        @NotNull
        @JsonProperty("new_status")
        UserStatus newStatus
) {
}
