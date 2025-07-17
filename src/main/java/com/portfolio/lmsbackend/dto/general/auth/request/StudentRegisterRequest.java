package com.portfolio.lmsbackend.dto.general.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.portfolio.lmsbackend.utils.StringsHelper.PASSWORD_REGEXP;

public record StudentRegisterRequest(
        @NotBlank
        @Size(max = 100)
        @JsonProperty("first_name")
        String firstName,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("last_name")
        String lastName,
        @NotBlank
        @Size(max = 255)
        @Email
        @JsonProperty("email")
        String email,
        @NotBlank
        @Pattern(regexp = PASSWORD_REGEXP)
        @JsonProperty("password")
        String password
) {
}
