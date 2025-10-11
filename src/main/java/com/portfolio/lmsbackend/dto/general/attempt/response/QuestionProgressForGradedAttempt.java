package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.GradedQuestionProgressStatus;

public class QuestionProgressForGradedAttempt extends QuestionProgress {
    private final GradedQuestionProgressStatus status;

    @JsonCreator
    public QuestionProgressForGradedAttempt(
            @JsonProperty("position") Integer position,
            @JsonProperty("status") GradedQuestionProgressStatus status
    ) {
        super(position);
        this.status = status;
    }

    @JsonProperty("status")
    public GradedQuestionProgressStatus status() {
        return status;
    }
}
