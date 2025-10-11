package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SingleChoiceAnswerContent extends AnswerContent {
    private final UUID choiceOptionId;

    @JsonCreator
    public SingleChoiceAnswerContent(
            @JsonProperty("choice_option_id") UUID choiceOptionId
    ) {
        this.choiceOptionId = choiceOptionId;
    }

    @JsonProperty("choice_option_id")
    public UUID choiceOptionId() {
        return choiceOptionId;
    }
}
