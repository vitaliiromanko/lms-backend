package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateQuestionGroupRequest(
        @NotBlank
        @JsonProperty("group_id")
        String groupId,
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title
) {
}
