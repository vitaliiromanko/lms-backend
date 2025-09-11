package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class CreateQuestionForExistingGroupRequest extends CreateQuestionRequest {
    @NotBlank
    private final String groupId;

    @JsonCreator
    public CreateQuestionForExistingGroupRequest(
            @JsonProperty("group_id") String groupId,
            @JsonProperty("new_question") NewQuestion newQuestion
    ) {
        super(newQuestion);
        this.groupId = groupId;
    }

    @JsonProperty("group_id")
    public String groupId() {
        return groupId;
    }
}
