package com.portfolio.lmsbackend.dto.staff.instructor.management.question.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.question.FillTheGapsQuestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FillTheGapsQuestionDetailed extends QuestionDetailed {
    @JsonView(Views.General.class)
    private final List<TextSegmentSummary> visibleTextSegments;

    @JsonView(Views.General.class)
    private final List<TextSegmentSummary> missingTextSegments;

    public FillTheGapsQuestionDetailed(FillTheGapsQuestion fillTheGapsQuestion) {
        super(fillTheGapsQuestion);
        this.visibleTextSegments = fillTheGapsQuestion.getVisibleTextSegments().stream()
                .map(TextSegmentSummary::new)
                .toList();
        this.missingTextSegments = fillTheGapsQuestion.getMissingTextSegments().stream()
                .map(TextSegmentSummary::new)
                .toList();
    }

    @JsonCreator
    protected FillTheGapsQuestionDetailed(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("visible_text_segments") List<TextSegmentSummary> visibleTextSegments,
            @JsonProperty("missing_text_segments") List<TextSegmentSummary> missingTextSegments,
            @JsonProperty("images") List<QuestionImageSummary> images,
            @JsonProperty("created_by") CreatedByResponse createdBy,
            @JsonProperty("created_at") LocalDateTime createdAt
    ) {
        super(id, type, images, createdBy, createdAt);
        this.visibleTextSegments = visibleTextSegments;
        this.missingTextSegments = missingTextSegments;
    }

    @JsonProperty("visible_text_segments")
    public List<TextSegmentSummary> visibleTextSegments() {
        return visibleTextSegments;
    }

    @JsonProperty("missing_text_segments")
    public List<TextSegmentSummary> missingTextSegments() {
        return missingTextSegments;
    }
}
