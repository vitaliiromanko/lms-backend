package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TextLongAnswerContent extends AnswerContent {
    private final String text;

    @JsonCreator
    public TextLongAnswerContent(
            @JsonProperty("text") String text
    ) {
        this.text = text;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }
}
