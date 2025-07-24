package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.model.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AdminUserSummary {
    private final UUID id;
    private final UserType type;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean emailVerified;
    private final UserStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    protected AdminUserSummary(User user) {
        this(
                user.getId(),
                user.getType(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEmailVerified(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @JsonCreator
    protected AdminUserSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") UserType type,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("email_verified") boolean emailVerified,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        this.id = id;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.emailVerified = emailVerified;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("type")
    public UserType type() {
        return type;
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

    @JsonProperty("status")
    public UserStatus status() {
        return status;
    }

    @JsonProperty("created_at")
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @JsonProperty("updated_at")
    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
