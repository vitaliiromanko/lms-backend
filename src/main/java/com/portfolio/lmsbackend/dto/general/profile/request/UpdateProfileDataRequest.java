package com.portfolio.lmsbackend.dto.general.profile.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public abstract class UpdateProfileDataRequest {
    @NotBlank
    @Size(max = 100)
    private final String firstName;

    @NotBlank
    @Size(max = 100)
    private final String lastName;

    @NotBlank
    @Size(max = 255)
    @Email
    private final String email;

    @JsonCreator
    public UpdateProfileDataRequest(
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
}
