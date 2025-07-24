package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.model.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class GetAdminUserSummaryResponse extends AdminUserSummary {
    private final String photoUrl;

    protected GetAdminUserSummaryResponse(User user) {
        super(user);
        this.photoUrl = user.getPhoto() != null ? user.getPhoto().getUrl() : null;
    }

    @JsonCreator
    protected GetAdminUserSummaryResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") UserType type,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("photo_url") String photoUrl,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, type, firstName, lastName, email, emailVerified, status, createdAt, updatedAt);
        this.photoUrl = photoUrl;
    }

    @JsonProperty("photo_url")
    public String photoUrl() {
        return photoUrl;
    }
}
