package com.portfolio.lmsbackend.dto.staff.instructor.management.student.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupBlockedAccessRestriction;

import java.time.LocalDateTime;

public class GetQuizGroupBlockedAccessRestrictionResponse extends GetQuizGroupAccessRestrictionResponse {
    public GetQuizGroupBlockedAccessRestrictionResponse(QuizGroupBlockedAccessRestriction accessRestriction) {
        super(accessRestriction);
    }

    @JsonCreator
    protected GetQuizGroupBlockedAccessRestrictionResponse(
            @JsonProperty("student") StudentSummary student,
            @JsonProperty("type") QuizGroupAccessRestrictionType type,
            @JsonProperty("created_at") LocalDateTime createdAt
    ) {
        super(student, type, createdAt);
    }
}
