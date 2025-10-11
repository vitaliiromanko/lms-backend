package com.portfolio.lmsbackend.dto.general.attempt.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StartAttemptRequest(
        @NotNull
        @JsonProperty("quiz_group_id")
        UUID quizGroupId
) {
}
