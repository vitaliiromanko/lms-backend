package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.content.quiz.TextSegment;

import java.util.UUID;

public record VisibleTextSegmentBody(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("text")
        String text
) {
    public VisibleTextSegmentBody(TextSegment textSegment) {
        this(
                textSegment.getId(),
                textSegment.getText()
        );
    }
}
