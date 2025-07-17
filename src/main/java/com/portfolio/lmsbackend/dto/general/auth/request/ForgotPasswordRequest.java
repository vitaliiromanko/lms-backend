package com.portfolio.lmsbackend.dto.general.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ForgotPasswordRequest(
        @NotBlank
        @Size(max = 255)
        @Email
        @JsonProperty("email")
        String email
) {
}
