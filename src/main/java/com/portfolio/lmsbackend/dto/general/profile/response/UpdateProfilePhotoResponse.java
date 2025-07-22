package com.portfolio.lmsbackend.dto.general.profile.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateProfilePhotoResponse(
        @JsonProperty("photo_url")
        String photoUrl
) {
}
