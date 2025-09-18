package com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.general.quiz.response.AttemptSummary;
import com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.quizgroupaccessrestriction.response.QuizGroupAccessRestrictionSummary;
import com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.quizgroupaccessrestriction.response.QuizGroupBlockedAccessRestrictionSummary;
import com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.quizgroupaccessrestriction.response.QuizGroupTimeWindowAccessRestrictionSummary;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.enums.content.SectionContentType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.portfolio.lmsbackend.service.application.helper.QuizGroupAccessRestrictionServiceHelper.mapQuizGroupAccessRestrictionTo;

public class GetStudentQuizGroupResponse extends GetQuizGroupResponse {
    @JsonView(Views.Basic.class)
    private final QuizGroupAccessRestrictionSummary accessRestriction;

    public GetStudentQuizGroupResponse(QuizGroup group, List<Attempt> attempts,
                                       QuizGroupAccessRestriction accessRestriction) {
        super(group, attempts);
        this.accessRestriction = accessRestriction == null
                ? null
                : mapQuizGroupAccessRestrictionTo(
                accessRestriction,
                QuizGroupBlockedAccessRestrictionSummary::new,
                QuizGroupTimeWindowAccessRestrictionSummary::new
        );
    }

    @JsonCreator
    protected GetStudentQuizGroupResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") SectionContentType type,
            @JsonProperty("title") String title,
            @JsonProperty("status") SectionContentStatus status,
            @JsonProperty("duration") Duration duration,
            @JsonProperty("max_attempts") Integer maxAttempts,
            @JsonProperty("access_restriction") QuizGroupAccessRestrictionSummary accessRestriction,
            @JsonProperty("attempts") List<AttemptSummary> attempts,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, type, title, status, duration, maxAttempts, attempts, createdAt, updatedAt);
        this.accessRestriction = accessRestriction;
    }

    @JsonProperty("access_restriction")
    public QuizGroupAccessRestrictionSummary accessRestriction() {
        return accessRestriction;
    }
}
