package com.portfolio.lmsbackend.dto.general.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.portfolio.lmsbackend.utils.StringsHelper.PASSWORD_REGEXP;

public record StaffRegisterRequest(
        @NotBlank
        @JsonProperty("invitation_token")
        String invitationToken,
        @NotBlank
        @Pattern(regexp = PASSWORD_REGEXP)
        @JsonProperty("password")
        String password
) {
}
