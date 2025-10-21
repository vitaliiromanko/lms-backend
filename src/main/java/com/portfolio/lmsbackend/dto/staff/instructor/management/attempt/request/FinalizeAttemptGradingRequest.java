package com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FinalizeAttemptGradingRequest(
        @NotNull
        @JsonProperty("attempt_id")
        UUID attemptId
) {
}
