package com.portfolio.lmsbackend.dto.general.profile.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.model.user.User;

import java.util.UUID;

public abstract class GetUserProfileResponse {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean emailVerified;
    private final String photoUrl;
    private final UserStatus status;

    protected GetUserProfileResponse(User user) {
        this(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEmailVerified(),
                user.getPhoto() != null ? user.getPhoto().getUrl() : null,
                user.getStatus()
        );
    }

    @JsonCreator
    protected GetUserProfileResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("photo_url") String photoUrl,
            @JsonProperty("status") UserStatus status
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.emailVerified = emailVerified;
        this.photoUrl = photoUrl;
        this.status = status;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("first_name")
    public String firstName() {
        return firstName;
    }

    @JsonProperty("last_name")
    public String lastName() {
        return lastName;
    }

    @JsonProperty("email")
    public String email() {
        return email;
    }

    @JsonProperty("email_verified")
    public boolean emailVerified() {
        return emailVerified;
    }

    @JsonProperty("photo_url")
    public String photoUrl() {
        return photoUrl;
    }

    @JsonProperty("status")
    public UserStatus status() {
        return status;
    }
}
