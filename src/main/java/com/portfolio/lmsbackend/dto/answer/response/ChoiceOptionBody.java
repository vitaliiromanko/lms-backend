package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;

import java.util.UUID;

public record ChoiceOptionBody(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("text")
        String text
) {
    public ChoiceOptionBody(ChoiceOption option) {
        this(
                option.getId(),
                option.getText()
        );
    }
}
