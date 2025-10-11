package com.portfolio.lmsbackend.dto.general.attempt.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GapAnswerSegmentRequest(
        @NotBlank
        @JsonProperty("text")
        String text,
        @NotNull
        @JsonProperty("missing_text_segment_id")
        UUID missingTextSegmentId
) {
}
