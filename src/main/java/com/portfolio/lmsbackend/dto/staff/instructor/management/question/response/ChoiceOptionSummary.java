package com.portfolio.lmsbackend.dto.staff.instructor.management.question.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;

import java.util.UUID;

public record ChoiceOptionSummary(
        @JsonView(Views.General.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.General.class)
        @JsonProperty("text")
        String text,
        @JsonView(Views.General.class)
        @JsonProperty("correct")
        Boolean correct
) {
    public ChoiceOptionSummary(ChoiceOption option) {
        this(
                option.getId(),
                option.getText(),
                option.getCorrect()
        );
    }
}
