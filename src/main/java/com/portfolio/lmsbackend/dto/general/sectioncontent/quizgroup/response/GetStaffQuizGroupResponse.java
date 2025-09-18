package com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.general.quiz.response.AttemptSummary;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.enums.content.SectionContentType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GetStaffQuizGroupResponse extends GetQuizGroupResponse {
    public GetStaffQuizGroupResponse(QuizGroup group, List<Attempt> attempts) {
        super(group, attempts);
    }

    @JsonCreator
    protected GetStaffQuizGroupResponse(
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
        super(id, type, title, status, duration, maxAttempts, attempts, createdAt, updatedAt);
    }
}
