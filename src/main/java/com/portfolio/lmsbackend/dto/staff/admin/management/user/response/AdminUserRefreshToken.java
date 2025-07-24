package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.token.RefreshToken;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminUserRefreshToken(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("token")
        String token,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("expired_at")
        LocalDateTime expiredAt
) {
    public AdminUserRefreshToken(RefreshToken refreshToken) {
        this(
                refreshToken.getId(),
                refreshToken.getToken(),
                refreshToken.getCreatedAt(),
                refreshToken.getExpiredAt()
        );
    }
}
