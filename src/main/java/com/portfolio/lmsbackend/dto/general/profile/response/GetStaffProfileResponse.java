package com.portfolio.lmsbackend.dto.general.profile.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.model.user.Staff;

import java.util.UUID;

public class GetStaffProfileResponse extends GetUserProfileResponse {
    private final StaffRole role;

    public GetStaffProfileResponse(Staff staff) {
        super(staff);
        this.role = staff.getRole();
    }

    @JsonCreator
    protected GetStaffProfileResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("photo_url") String photoUrl,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("role") StaffRole role
    ) {
        super(id, firstName, lastName, email, emailVerified, photoUrl, status);
        this.role = role;
    }

    @JsonProperty("role")
    public StaffRole role() {
        return role;
    }
}
