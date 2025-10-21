package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record GapAnswerContent(
        @JsonProperty("missing_text_segment_id")
        UUID missingTextSegmentId,
        @JsonProperty("text")
        String text
) {
}
