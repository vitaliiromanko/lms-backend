package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.model.user.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AdminUserProfile extends AdminUserSummary {
    private final AdminUserPhoto photo;
    private final Set<AdminUserRefreshToken> refreshTokens;

    protected AdminUserProfile(User user) {
        super(user);
        this.photo = user.getPhoto() != null ? new AdminUserPhoto(user.getPhoto()) : null;
        this.refreshTokens = user.getRefreshTokens().stream()
                .map(AdminUserRefreshToken::new)
                .collect(Collectors.toSet());
    }

    @JsonCreator
    protected AdminUserProfile(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") UserType type,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("photo") AdminUserPhoto photo,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt,
            @JsonProperty("refresh_tokens") Set<AdminUserRefreshToken> refreshTokens
    ) {
        super(id, type, firstName, lastName, email, emailVerified, status, createdAt, updatedAt);
        this.photo = photo;
        this.refreshTokens = refreshTokens;
    }

    @JsonProperty("photo")
    public AdminUserPhoto photo() {
        return photo;
    }

    @JsonProperty("refresh_tokens")
    public Set<AdminUserRefreshToken> refreshTokens() {
        return refreshTokens;
    }
}
