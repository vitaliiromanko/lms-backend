package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class FillTheGapsAnswerContent extends AnswerContent {
    private final Set<GapAnswerContent> gapAnswerContents;

    @JsonCreator
    public FillTheGapsAnswerContent(
            @JsonProperty("gap_answer_contents") Set<GapAnswerContent> gapAnswerContents
    ) {
        this.gapAnswerContents = gapAnswerContents;
    }

    @JsonProperty("gap_answer_contents")
    public Set<GapAnswerContent> gapAnswerContents() {
        return gapAnswerContents;
    }
}
