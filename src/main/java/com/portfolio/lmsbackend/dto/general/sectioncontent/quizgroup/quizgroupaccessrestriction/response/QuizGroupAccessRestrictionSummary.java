package com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.quizgroupaccessrestriction.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;

public abstract class QuizGroupAccessRestrictionSummary {
    @JsonView(Views.Basic.class)
    private final QuizGroupAccessRestrictionType type;

    protected QuizGroupAccessRestrictionSummary(QuizGroupAccessRestriction accessRestriction) {
        this(accessRestriction.getType());
    }

    @JsonCreator
    protected QuizGroupAccessRestrictionSummary(
            @JsonProperty("type") QuizGroupAccessRestrictionType type
    ) {
        this.type = type;
    }

    @JsonProperty("type")
    public QuizGroupAccessRestrictionType type() {
        return type;
    }
}
