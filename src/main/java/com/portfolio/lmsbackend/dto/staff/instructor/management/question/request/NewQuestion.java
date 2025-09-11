package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewSingleChoiceQuestion.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = NewMultipleChoiceQuestion.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = NewFillTheGapsQuestion.class, name = "FILL_THE_GAPS"),
        @JsonSubTypes.Type(value = NewTextLongQuestion.class, name = "TEXT_LONG")
})
public abstract class NewQuestion {
}
