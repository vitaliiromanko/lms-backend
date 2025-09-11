package com.portfolio.lmsbackend.dto.staff.instructor.management.student.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupTimeWindowAccessRestriction;

import java.time.LocalDateTime;

public class GetQuizGroupTimeWindowAccessRestrictionResponse extends GetQuizGroupAccessRestrictionResponse {
    private final LocalDateTime availableFrom;
    private final LocalDateTime availableUntil;

    public GetQuizGroupTimeWindowAccessRestrictionResponse(QuizGroupTimeWindowAccessRestriction accessRestriction) {
        super(accessRestriction);
        this.availableFrom = accessRestriction.getAvailableFrom();
        this.availableUntil = accessRestriction.getAvailableUntil();
    }

    @JsonCreator
    protected GetQuizGroupTimeWindowAccessRestrictionResponse(
            @JsonProperty("student") StudentSummary student,
            @JsonProperty("type") QuizGroupAccessRestrictionType type,
            @JsonProperty("available_from") LocalDateTime availableFrom,
            @JsonProperty("available_until") LocalDateTime availableUntil,
            @JsonProperty("created_at") LocalDateTime createdAt
    ) {
        super(student, type, createdAt);
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
    }

    @JsonProperty("available_from")
    public LocalDateTime availableFrom() {
        return availableFrom;
    }

    @JsonProperty("available_until")
    public LocalDateTime availableUntil() {
        return availableUntil;
    }
}
