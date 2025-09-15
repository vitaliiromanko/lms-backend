package com.portfolio.lmsbackend.dto.staff.admin.management.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAdminStaffRoleRequest(
        @NotNull
        @JsonProperty("staff_id")
        UUID staffId,
        @NotNull
        @JsonProperty("new_role")
        StaffRole newRole
) {
}
