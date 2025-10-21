package com.portfolio.lmsbackend.dto.staff.instructor.management.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.user.User;

import java.util.UUID;

public record UserSummary(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("photo_url")
        String photoUrl,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email")
        String email
) {
    public UserSummary(User user) {
        this(
                user.getId(),
                user.getPhoto() != null ? user.getPhoto().getUrl() : null,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
