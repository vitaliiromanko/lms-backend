package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response.QuestionGroupSummary;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.UserQuestionGroupTopic;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetQuestionGroupTopicResponse extends QuestionGroupTopicSummary {
    @JsonView(Views.General.class)
    private final QuestionGroupTopicSummary parent;

    @JsonView(Views.General.class)
    private final List<QuestionGroupTopicSummary> children;

    @JsonView(Views.General.class)
    private final List<QuestionGroupSummary> questionGroups;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime createdAt;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime updatedAt;

    public GetQuestionGroupTopicResponse(UserQuestionGroupTopic topic) {
        super(topic);
        this.parent = topic.getParent() != null ? new QuestionGroupTopicSummary(topic.getParent()) : null;
        this.children = topic.getChildren().stream()
                .map(QuestionGroupTopicSummary::new)
                .sorted(Comparator.comparing(QuestionGroupTopicSummary::title))
                .collect(Collectors.toList());
        this.questionGroups = topic.getQuestionGroups().stream()
                .map(QuestionGroupSummary::new)
                .sorted(Comparator.comparing(QuestionGroupSummary::title))
                .collect(Collectors.toList());
        this.createdAt = topic.getCreatedAt();
        this.updatedAt = topic.getUpdatedAt();
    }

    @JsonCreator
    public GetQuestionGroupTopicResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("parent") QuestionGroupTopicSummary parent,
            @JsonProperty("children") List<QuestionGroupTopicSummary> children,
            @JsonProperty("question_groups") List<QuestionGroupSummary> questionGroups,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, title);
        this.parent = parent;
        this.children = children;
        this.questionGroups = questionGroups;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonProperty("parent")
    public QuestionGroupTopicSummary parent() {
        return parent;
    }

    @JsonProperty("children")
    public List<QuestionGroupTopicSummary> children() {
        return children;
    }

    @JsonProperty("question_groups")
    public List<QuestionGroupSummary> questionGroups() {
        return questionGroups;
    }

    @JsonProperty("created_at")
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @JsonProperty("updated_at")
    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
