package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateQuizForNewGroupRequest.class, name = "NEW_GROUP"),
        @JsonSubTypes.Type(value = CreateQuizForExistingGroupRequest.class, name = "EXISTING_GROUP")
})
public abstract class CreateQuizRequest {
    @Valid
    @NotNull
    private final NewQuiz newQuiz;

    @JsonCreator
    protected CreateQuizRequest(
            @JsonProperty("new_quiz") NewQuiz newQuiz
    ) {
        this.newQuiz = newQuiz;
    }

    @JsonProperty("new_quiz")
    public NewQuiz newQuiz() {
        return newQuiz;
    }
}
