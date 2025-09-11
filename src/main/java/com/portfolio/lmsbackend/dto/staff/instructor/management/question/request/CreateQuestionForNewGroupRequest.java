package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateQuestionForNewGroupRequest extends CreateQuestionRequest {
    private final String topicId;

    @NotBlank
    @Size(max = 100)
    private final String title;

    @JsonCreator
    public CreateQuestionForNewGroupRequest(
            @JsonProperty("topic_id") String topicId,
            @JsonProperty("title") String title,
            @JsonProperty("new_question") NewQuestion newQuestion
    ) {
        super(newQuestion);
        this.topicId = topicId;
        this.title = title;
    }

    @JsonProperty("topic_id")
    public String topicId() {
        return topicId;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }
}
