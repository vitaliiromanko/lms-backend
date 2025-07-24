package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.model.user.Staff;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class GetAdminStaffProfileResponse extends GetAdminUserProfileResponse {
    private final StaffRole role;

    public GetAdminStaffProfileResponse(Staff staff) {
        super(staff);
        this.role = staff.getRole();
    }

    @JsonCreator
    protected GetAdminStaffProfileResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") UserType type,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("photo") AdminUserPhoto photo,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("role") StaffRole role,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt,
            @JsonProperty("refresh_tokens") Set<AdminUserRefreshToken> refreshTokens
    ) {
        super(id, type, firstName, lastName, email, emailVerified, photo, status, createdAt, updatedAt, refreshTokens);
        this.role = role;
    }

    @JsonProperty("role")
    public StaffRole role() {
        return role;
    }
}
