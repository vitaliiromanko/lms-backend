package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.model.user.Staff;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetAdminStaffSummaryResponse extends GetAdminUserSummaryResponse {
    private final StaffRole role;

    public GetAdminStaffSummaryResponse(Staff staff) {
        super(staff);
        this.role = staff.getRole();
    }

    @JsonCreator
    protected GetAdminStaffSummaryResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") UserType type,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("photo_url") String photoUrl,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("role") StaffRole role,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, type, firstName, lastName, email, emailVerified, photoUrl, status, createdAt, updatedAt);
        this.role = role;
    }

    @JsonProperty("role")
    public StaffRole role() {
        return role;
    }
}
