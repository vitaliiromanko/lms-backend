package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateQuizForExistingGroupRequest extends CreateQuizRequest {
    @NotNull
    private final UUID groupId;

    @JsonCreator
    public CreateQuizForExistingGroupRequest(
            @JsonProperty("group_id") UUID groupId,
            @JsonProperty("new_quiz") NewQuiz newQuiz
    ) {
        super(newQuiz);
        this.groupId = groupId;
    }

    @JsonProperty("group_id")
    public UUID groupId() {
        return groupId;
    }
}
