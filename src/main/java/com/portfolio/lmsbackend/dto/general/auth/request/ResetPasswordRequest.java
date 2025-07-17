package com.portfolio.lmsbackend.dto.general.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.portfolio.lmsbackend.utils.StringsHelper.PASSWORD_REGEXP;

public record ResetPasswordRequest(
        @NotBlank
        @JsonProperty("reset_password_token")
        String resetPasswordToken,
        @NotBlank
        @Pattern(regexp = PASSWORD_REGEXP)
        @JsonProperty("new_password")
        String newPassword
) {
}
