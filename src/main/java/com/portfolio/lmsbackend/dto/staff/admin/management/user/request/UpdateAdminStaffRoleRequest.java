package com.portfolio.lmsbackend.dto.staff.admin.management.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAdminStaffRoleRequest(
        @NotBlank
        @JsonProperty("staff_id")
        String staffId,
        @NotNull
        @JsonProperty("new_role")
        StaffRole newRole
) {
}
