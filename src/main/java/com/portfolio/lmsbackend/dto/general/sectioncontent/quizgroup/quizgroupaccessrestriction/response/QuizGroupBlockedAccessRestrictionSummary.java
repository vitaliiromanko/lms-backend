package com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.quizgroupaccessrestriction.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupBlockedAccessRestriction;

public class QuizGroupBlockedAccessRestrictionSummary extends QuizGroupAccessRestrictionSummary {
    public QuizGroupBlockedAccessRestrictionSummary(QuizGroupBlockedAccessRestriction accessRestriction) {
        this(accessRestriction.getType());
    }

    @JsonCreator
    protected QuizGroupBlockedAccessRestrictionSummary(
            @JsonProperty("type") QuizGroupAccessRestrictionType type
    ) {
        super(type);
    }
}
