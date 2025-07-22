package com.portfolio.lmsbackend.dto.general.profile.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.portfolio.lmsbackend.utils.StringsHelper.PASSWORD_REGEXP;

public record UpdateProfilePasswordRequest(
        @NotBlank
        @Pattern(regexp = PASSWORD_REGEXP)
        @JsonProperty("old_password")
        String oldPassword,
        @NotBlank
        @Pattern(regexp = PASSWORD_REGEXP)
        @JsonProperty("new_password")
        String newPassword
) {
}
