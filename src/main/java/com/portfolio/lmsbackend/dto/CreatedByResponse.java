package com.portfolio.lmsbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.model.user.Staff;

import java.util.UUID;

public record CreatedByResponse(
        @JsonView(Views.Detailed.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.Detailed.class)
        @JsonProperty("photo_url")
        String photoUrl,
        @JsonView(Views.Detailed.class)
        @JsonProperty("first_name")
        String firstName,
        @JsonView(Views.Detailed.class)
        @JsonProperty("last_name")
        String lastName
) {
    public CreatedByResponse(Staff staff) {
        this(
                staff.getId(),
                staff.getPhoto() != null ? staff.getPhoto().getUrl() : null,
                staff.getFirstName(),
                staff.getLastName()
        );
    }
}
