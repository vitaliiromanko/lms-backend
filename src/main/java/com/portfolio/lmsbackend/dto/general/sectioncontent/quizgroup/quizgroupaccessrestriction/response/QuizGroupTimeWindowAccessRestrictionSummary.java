package com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.quizgroupaccessrestriction.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupTimeWindowAccessRestriction;

import java.time.LocalDateTime;

public class QuizGroupTimeWindowAccessRestrictionSummary extends QuizGroupAccessRestrictionSummary {
    @JsonView(Views.Basic.class)
    private final LocalDateTime availableFrom;

    @JsonView(Views.Basic.class)
    private final LocalDateTime availableUntil;

    public QuizGroupTimeWindowAccessRestrictionSummary(QuizGroupTimeWindowAccessRestriction accessRestriction) {
        super(accessRestriction);
        this.availableFrom = accessRestriction.getAvailableFrom();
        this.availableUntil = accessRestriction.getAvailableUntil();
    }

    @JsonCreator
    protected QuizGroupTimeWindowAccessRestrictionSummary(
            @JsonProperty("type") QuizGroupAccessRestrictionType type,
            @JsonProperty("available_from") LocalDateTime availableFrom,
            @JsonProperty("available_until") LocalDateTime availableUntil
    ) {
        super(type);
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
