package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewChoiceOption(
        @NotBlank
        @JsonProperty("text")
        String text,
        @NotNull
        @JsonProperty("correct")
        Boolean correct
) {
}
