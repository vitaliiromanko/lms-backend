package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class NewTextLongQuestion extends NewQuestion {
    @NotBlank
    private final String text;

    @JsonCreator
    public NewTextLongQuestion(
            @JsonProperty("text") String text
    ) {
        this.text = text;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }
}
