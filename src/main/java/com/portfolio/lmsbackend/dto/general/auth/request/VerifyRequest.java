package com.portfolio.lmsbackend.dto.general.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record VerifyRequest(
        @NotBlank
        @JsonProperty("verification_token")
        String verificationToken
) {
}
