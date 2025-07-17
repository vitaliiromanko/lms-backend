package com.portfolio.lmsbackend.dto.general.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VerifyResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
