package com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.general.quiz.response.AttemptSummary;
import com.portfolio.lmsbackend.dto.general.sectioncontent.response.GetSectionContentResponse;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.enums.content.SectionContentType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public abstract class GetQuizGroupResponse extends GetSectionContentResponse {
    @JsonView(Views.Basic.class)
    private final Duration duration;

    @JsonView(Views.Basic.class)
    private final Integer maxAttempts;

    @JsonView(Views.Basic.class)
    private final List<AttemptSummary> attempts;

    protected GetQuizGroupResponse(QuizGroup group, List<Attempt> attempts) {
        super(group);
        this.duration = group.getDuration();
        this.maxAttempts = group.getMaxAttempts();
        this.attempts = attempts.stream()
                .map(AttemptSummary::new)
                .sorted(Comparator.comparing(AttemptSummary::createdAt))
                .toList();
    }

    @JsonCreator
    protected GetQuizGroupResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") SectionContentType type,
            @JsonProperty("title") String title,
            @JsonProperty("status") SectionContentStatus status,
            @JsonProperty("duration") Duration duration,
            @JsonProperty("max_attempts") Integer maxAttempts,
            @JsonProperty("attempts") List<AttemptSummary> attempts,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, type, title, status, createdAt, updatedAt);
        this.duration = duration;
        this.maxAttempts = maxAttempts;
        this.attempts = attempts;
    }

    @JsonProperty("duration")
    public Duration duration() {
        return duration;
    }

    @JsonProperty("max_attempts")
    public Integer maxAttempts() {
        return maxAttempts;
    }

    @JsonProperty("attempts")
    public List<AttemptSummary> attempts() {
        return attempts;
    }
}
