package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateQuestionGroupTopicRequest(
        @NotBlank
        @Size(max = 100)
        @JsonProperty("title")
        String title,
        @JsonProperty("parent_id")
        String parentId
) {
}
