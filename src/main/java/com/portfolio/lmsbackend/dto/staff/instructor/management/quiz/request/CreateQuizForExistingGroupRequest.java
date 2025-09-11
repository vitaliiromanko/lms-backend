package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class CreateQuizForExistingGroupRequest extends CreateQuizRequest {
    @NotBlank
    private final String groupId;

    @JsonCreator
    public CreateQuizForExistingGroupRequest(
            @JsonProperty("group_id") String groupId,
            @JsonProperty("new_quiz") NewQuiz newQuiz
    ) {
        super(newQuiz);
        this.groupId = groupId;
    }

    @JsonProperty("group_id")
    public String groupId() {
        return groupId;
    }
}
