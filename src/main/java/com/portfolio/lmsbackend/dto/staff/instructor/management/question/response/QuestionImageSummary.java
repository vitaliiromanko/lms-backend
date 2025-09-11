package com.portfolio.lmsbackend.dto.staff.instructor.management.question.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.model.media.image.QuestionImage;

import java.util.UUID;

public record QuestionImageSummary(
        @JsonView(Views.General.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.General.class)
        @JsonProperty("url")
        String url
) {
    public QuestionImageSummary(QuestionImage image) {
        this(
                image.getId(),
                image.getUrl()
        );
    }
}
