package com.portfolio.lmsbackend.dto.general.attempt.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public class SubmitMultipleChoiceAnswerRequest extends SubmitAnswerRequest {
    @NotEmpty
    private final Set<@NotNull UUID> selectedOptionIds;

    @JsonCreator
    public SubmitMultipleChoiceAnswerRequest(
            @JsonProperty("answer_id") UUID answerId,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("selected_option_ids") Set<UUID> selectedOptionIds
    ) {
        super(answerId, type);
        this.selectedOptionIds = selectedOptionIds;
    }

    @JsonProperty("selected_option_ids")
    public Set<UUID> selectedOptionIds() {
        return selectedOptionIds;
    }
}
