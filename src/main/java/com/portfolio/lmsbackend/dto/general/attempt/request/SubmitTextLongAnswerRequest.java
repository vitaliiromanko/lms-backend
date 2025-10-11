package com.portfolio.lmsbackend.dto.general.attempt.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class SubmitTextLongAnswerRequest extends SubmitAnswerRequest {
    @NotBlank
    private final String text;

    @JsonCreator
    public SubmitTextLongAnswerRequest(
            @JsonProperty("answer_id") UUID answerId,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("text") String text
    ) {
        super(answerId, type);
        this.text = text;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }
}
