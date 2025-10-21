package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.media.image.QuestionImage;

import java.util.UUID;

public record QuestionImageBody(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("url")
        String url
) {
    public QuestionImageBody(QuestionImage image) {
        this(
                image.getId(),
                image.getUrl()
        );
    }
}
