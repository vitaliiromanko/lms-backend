package com.portfolio.lmsbackend.dto.general.attempt.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.validation.annotation.UniqueMissingTextSegmentId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

@UniqueMissingTextSegmentId
public class SubmitFillTheGapsAnswerRequest extends SubmitAnswerRequest {
    @NotEmpty
    private final Set<@Valid GapAnswerSegmentRequest> gapAnswerSegments;

    @JsonCreator
    public SubmitFillTheGapsAnswerRequest(
            @JsonProperty("answer_id") UUID answerId,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("gap_answer_segments") Set<GapAnswerSegmentRequest> gapAnswerSegments
    ) {
        super(answerId, type);
        this.gapAnswerSegments = gapAnswerSegments;
    }

    @JsonProperty("gap_answer_segments")
    public Set<GapAnswerSegmentRequest> gapAnswerSegments() {
        return gapAnswerSegments;
    }
}
