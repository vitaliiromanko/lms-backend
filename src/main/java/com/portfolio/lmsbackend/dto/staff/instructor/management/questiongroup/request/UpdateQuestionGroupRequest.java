package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateQuestionGroupRequest(
        @NotNull
        @JsonProperty("group_id")
        UUID groupId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
