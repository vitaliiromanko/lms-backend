package com.portfolio.lmsbackend.dto.staff.instructor.management.question.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.question.TextLongQuestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TextLongQuestionDetailed extends QuestionDetailed {
    @JsonView(Views.General.class)
    private final String text;

    public TextLongQuestionDetailed(TextLongQuestion textLongQuestion) {
        super(textLongQuestion);
        this.text = textLongQuestion.getText();
    }

    @JsonCreator
    protected TextLongQuestionDetailed(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("text") String text,
            @JsonProperty("images") List<QuestionImageSummary> images,
            @JsonProperty("created_by") CreatedByResponse createdBy,
            @JsonProperty("created_at") LocalDateTime createdAt
    ) {
        super(id, type, images, createdBy, createdAt);
        this.text = text;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }
}
