package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.media.image.UserPhoto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminUserPhoto(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("original_filename")
        String originalFilename,
        @JsonProperty("url")
        String url,
        @JsonProperty("public_id")
        String publicId,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
    public AdminUserPhoto(UserPhoto photo) {
        this(
                photo.getId(),
                photo.getOriginalFilename(),
                photo.getUrl(),
                photo.getPublicId(),
                photo.getCreatedAt()
        );
    }
}
