package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateQuestionGroupStatusRequest(
        @NotNull
        @JsonProperty("group_id")
        UUID groupId,
        @NotNull
        @JsonProperty("new_status")
        QuestionGroupStatus newStatus
) {
}
