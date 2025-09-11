package com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.validation.annotation.NonNegativeDuration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.Duration;

public record UpdateQuizGroupRequest(
        @NotBlank
        @JsonProperty("group_id")
        String groupId,
        @NonNegativeDuration
        @JsonProperty("duration")
        Duration duration,
        @Min(value = 1)
        @JsonProperty("max_attempts")
        Integer maxAttempts
) {
}
