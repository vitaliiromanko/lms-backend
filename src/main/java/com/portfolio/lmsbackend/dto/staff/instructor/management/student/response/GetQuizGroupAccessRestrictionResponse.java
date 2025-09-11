package com.portfolio.lmsbackend.dto.staff.instructor.management.student.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;

import java.time.LocalDateTime;

public abstract class GetQuizGroupAccessRestrictionResponse {
    private final StudentSummary student;
    private final QuizGroupAccessRestrictionType type;
    private final LocalDateTime createdAt;

    protected GetQuizGroupAccessRestrictionResponse(QuizGroupAccessRestriction accessRestriction) {
        this(
                new StudentSummary(accessRestriction.getStudent()),
                accessRestriction.getType(),
                accessRestriction.getCreatedAt()
        );
    }

    @JsonCreator
    protected GetQuizGroupAccessRestrictionResponse(
            @JsonProperty("student") StudentSummary student,
            @JsonProperty("type") QuizGroupAccessRestrictionType type,
            @JsonProperty("created_at") LocalDateTime createdAt
    ) {
        this.student = student;
        this.type = type;
        this.createdAt = createdAt;
    }

    @JsonProperty("student")
    public StudentSummary student() {
        return student;
    }

    @JsonProperty("type")
    public QuizGroupAccessRestrictionType type() {
        return type;
    }

    @JsonProperty("created_at")
    public LocalDateTime createdAt() {
        return createdAt;
    }
}
