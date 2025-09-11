package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateQuestionGroupStatusRequest(
        @NotBlank
        @JsonProperty("group_id")
        String groupId,
        @NotNull
        @JsonProperty("new_status")
        QuestionGroupStatus newStatus
) {
}
