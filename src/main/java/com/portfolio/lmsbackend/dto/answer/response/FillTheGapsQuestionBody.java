package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.content.quiz.TextSegment;
import com.portfolio.lmsbackend.model.content.quiz.answer.FillTheGapsAnswer;
import com.portfolio.lmsbackend.model.content.quiz.question.FillTheGapsQuestion;

import java.util.List;
import java.util.UUID;

public class FillTheGapsQuestionBody extends QuestionBody {
    private final List<VisibleTextSegmentBody> visibleTextSegments;
    private final List<UUID> missingTextSegmentIds;

    public FillTheGapsQuestionBody(FillTheGapsAnswer fillTheGapsAnswer) {
        super(fillTheGapsAnswer);

        FillTheGapsQuestion fillTheGapsQuestion = (FillTheGapsQuestion) fillTheGapsAnswer.getQuizQuestion().getQuestion();

        this.visibleTextSegments = fillTheGapsQuestion.getVisibleTextSegments().stream()
                .map(VisibleTextSegmentBody::new)
                .toList();
        this.missingTextSegmentIds = fillTheGapsQuestion.getMissingTextSegments().stream()
                .map(TextSegment::getId)
                .toList();
    }

    @JsonCreator
    protected FillTheGapsQuestionBody(
            @JsonProperty("visible_text_segments") List<VisibleTextSegmentBody> visibleTextSegments,
            @JsonProperty("missing_text_segment_ids") List<UUID> missingTextSegmentIds,
            @JsonProperty("images") List<QuestionImageBody> images
    ) {
        super(images);
        this.visibleTextSegments = visibleTextSegments;
        this.missingTextSegmentIds = missingTextSegmentIds;
    }

    @JsonProperty("visible_text_segments")
    public List<VisibleTextSegmentBody> visibleTextSegments() {
        return visibleTextSegments;
    }

    @JsonProperty("missing_text_segment_ids")
    public List<UUID> missingTextSegmentIds() {
        return missingTextSegmentIds;
    }
}
