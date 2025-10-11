package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class QuestionProgress {
    private final Integer position;

    @JsonCreator
    protected QuestionProgress(
            @JsonProperty("position") Integer position
    ) {
        this.position = position;
    }

    @JsonProperty("position")
    public Integer position() {
        return position;
    }
}
