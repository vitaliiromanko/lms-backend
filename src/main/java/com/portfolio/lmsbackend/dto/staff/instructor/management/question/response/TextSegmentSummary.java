package com.portfolio.lmsbackend.dto.staff.instructor.management.question.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.model.content.quiz.TextSegment;

import java.util.UUID;

public record TextSegmentSummary(
        @JsonView(Views.General.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.General.class)
        @JsonProperty("text")
        String text
) {
    public TextSegmentSummary(TextSegment textSegment) {
        this(
                textSegment.getId(),
                textSegment.getText()
        );
    }
}
