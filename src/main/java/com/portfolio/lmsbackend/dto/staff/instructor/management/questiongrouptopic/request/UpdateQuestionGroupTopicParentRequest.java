package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateQuestionGroupTopicParentRequest(
        @NotNull
        @JsonProperty("topic_id")
        UUID topicId,
        @JsonProperty("new_parent_id")
        UUID newParentId
) {
}
