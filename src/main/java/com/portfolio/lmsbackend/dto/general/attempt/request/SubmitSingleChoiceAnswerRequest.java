package com.portfolio.lmsbackend.dto.general.attempt.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class SubmitSingleChoiceAnswerRequest extends SubmitAnswerRequest {
    @NotNull
    private final UUID selectedOptionId;

    @JsonCreator
    public SubmitSingleChoiceAnswerRequest(
            @JsonProperty("answer_id") UUID answerId,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("selected_option_id") UUID selectedOptionId
    ) {
        super(answerId, type);
        this.selectedOptionId = selectedOptionId;
    }

    @JsonProperty("selected_option_id")
    public UUID selectedOptionId() {
        return selectedOptionId;
    }
}
