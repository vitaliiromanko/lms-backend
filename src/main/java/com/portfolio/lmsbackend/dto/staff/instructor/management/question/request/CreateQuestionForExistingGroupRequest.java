package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateQuestionForExistingGroupRequest extends CreateQuestionRequest {
    @NotNull
    private final UUID groupId;

    @JsonCreator
    public CreateQuestionForExistingGroupRequest(
            @JsonProperty("group_id") UUID groupId,
            @JsonProperty("new_question") NewQuestion newQuestion
    ) {
        super(newQuestion);
        this.groupId = groupId;
    }

    @JsonProperty("group_id")
    public UUID groupId() {
        return groupId;
    }
}
