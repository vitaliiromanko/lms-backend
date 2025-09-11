package com.portfolio.lmsbackend.dto.staff.instructor.management.question.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.question.Question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public abstract class QuestionDetailed {
    @JsonView(Views.General.class)
    private final UUID id;

    @JsonView(Views.General.class)
    private final QuestionType type;

    @JsonView(Views.General.class)
    private final List<QuestionImageSummary> images;

    @JsonView(Views.Detailed.class)
    private final CreatedByResponse createdBy;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime createdAt;

    protected QuestionDetailed(Question question) {
        this(
                question.getId(),
                question.getType(),
                question.getImages().stream()
                        .map(QuestionImageSummary::new)
                        .toList(),
                new CreatedByResponse(question.getCreatedBy()),
                question.getCreatedAt()
        );
    }

    @JsonCreator
    protected QuestionDetailed(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("images") List<QuestionImageSummary> images,
            @JsonProperty("created_by") CreatedByResponse createdBy,
            @JsonProperty("created_at") LocalDateTime createdAt
    ) {
        this.id = id;
        this.type = type;
        this.images = images;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("type")
    public QuestionType type() {
        return type;
    }

    @JsonProperty("images")
    public List<QuestionImageSummary> images() {
        return images;
    }

    @JsonProperty("created_by")
    public CreatedByResponse createdBy() {
        return createdBy;
    }

    @JsonProperty("created_at")
    public LocalDateTime createdAt() {
        return createdAt;
    }
}
