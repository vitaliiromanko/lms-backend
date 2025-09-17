package com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.response.QuizDetailed;
import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record GetQuizGroupDetailedResponse(
        @JsonView(Views.General.class)
        @JsonProperty("id")
        UUID id,
        @JsonView(Views.General.class)
        @JsonProperty("title")
        String title,
        @JsonView(Views.General.class)
        @JsonProperty("status")
        SectionContentStatus status,
        @JsonView(Views.General.class)
        @JsonProperty("duration")
        Duration duration,
        @JsonView(Views.General.class)
        @JsonProperty("max_attempts")
        Integer maxAttempts,
        @JsonView(Views.General.class)
        @JsonProperty("actual_quiz")
        QuizDetailed actualQuiz,
        @JsonView(Views.General.class)
        @JsonProperty("history_changes")
        List<QuizDetailed> historyChanges,
        @JsonView(Views.Detailed.class)
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonView(Views.Detailed.class)
        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    public GetQuizGroupDetailedResponse(QuizGroup group) {
        this(
                group.getId(),
                group.getTitle(),
                group.getStatus(),
                group.getDuration(),
                group.getMaxAttempts(),
                new QuizDetailed(group.getQuizzes().getLast()),
                group.getQuizzes().size() > 1
                        ? group.getQuizzes().subList(0, group.getQuizzes().size() - 1).stream()
                        .map(QuizDetailed::new)
                        .toList()
                        : Collections.emptyList(),
                group.getCreatedAt(),
                group.getUpdatedAt()
        );
    }
}
