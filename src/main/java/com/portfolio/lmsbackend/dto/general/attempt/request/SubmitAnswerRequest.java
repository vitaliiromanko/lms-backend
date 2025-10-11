package com.portfolio.lmsbackend.dto.general.attempt.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SubmitSingleChoiceAnswerRequest.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = SubmitMultipleChoiceAnswerRequest.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = SubmitFillTheGapsAnswerRequest.class, name = "FILL_THE_GAPS"),
        @JsonSubTypes.Type(value = SubmitTextLongAnswerRequest.class, name = "TEXT_LONG")
})
public abstract class SubmitAnswerRequest {
    @NotNull
    private final UUID answerId;

    @NotNull
    private final QuestionType type;

    @JsonCreator
    protected SubmitAnswerRequest(
            @JsonProperty("answer_id") UUID answerId,
            @JsonProperty("type") QuestionType type
    ) {
        this.answerId = answerId;
        this.type = type;
    }

    @JsonProperty("answer_id")
    public UUID answerId() {
        return answerId;
    }

    @JsonProperty("type")
    public QuestionType type() {
        return type;
    }
}
