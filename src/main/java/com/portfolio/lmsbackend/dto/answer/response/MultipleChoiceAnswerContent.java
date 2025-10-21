package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.UUID;

public class MultipleChoiceAnswerContent extends AnswerContent {
    private final Set<UUID> choiceOptionIds;

    @JsonCreator
    public MultipleChoiceAnswerContent(
            @JsonProperty("choice_option_ids") Set<UUID> choiceOptionIds
    ) {
        this.choiceOptionIds = choiceOptionIds;
    }

    @JsonProperty("choice_option_ids")
    public Set<UUID> choiceOptionIds() {
        return choiceOptionIds;
    }
}
