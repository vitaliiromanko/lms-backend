package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionProgressForStartedAttempt extends QuestionProgress {
    private final Boolean answered;

    @JsonCreator
    public QuestionProgressForStartedAttempt(
            @JsonProperty("position") Integer position,
            @JsonProperty("answered") Boolean answered
    ) {
        super(position);
        this.answered = answered;
    }

    @JsonProperty("answered")
    public Boolean answered() {
        return answered;
    }
}
