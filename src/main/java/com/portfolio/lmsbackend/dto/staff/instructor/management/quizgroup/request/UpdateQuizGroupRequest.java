package com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.validation.annotation.NonNegativeDuration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.util.UUID;

public record UpdateQuizGroupRequest(
        @NotNull
        @JsonProperty("group_id")
        UUID groupId,
        @NonNegativeDuration
        @JsonProperty("duration")
        Duration duration,
        @Min(value = 1)
        @JsonProperty("max_attempts")
        Integer maxAttempts
) {
}
