package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateQuestionGroupTopicParentRequest(
        @NotBlank
        @JsonProperty("topic_id")
        String topicId,
        @JsonProperty("new_parent_id")
        String newParentId
) {
}
