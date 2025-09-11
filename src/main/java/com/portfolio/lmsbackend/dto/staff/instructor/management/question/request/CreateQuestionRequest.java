package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateQuestionForNewGroupRequest.class, name = "NEW_GROUP"),
        @JsonSubTypes.Type(value = CreateQuestionForExistingGroupRequest.class, name = "EXISTING_GROUP")
})
public abstract class CreateQuestionRequest {
    @Valid
    @NotNull
    private final NewQuestion newQuestion;

    @JsonCreator
    protected CreateQuestionRequest(
            @JsonProperty("new_question") NewQuestion newQuestion
    ) {
        this.newQuestion = newQuestion;
    }

    @JsonProperty("new_question")
    public NewQuestion newQuestion() {
        return newQuestion;
    }
}
