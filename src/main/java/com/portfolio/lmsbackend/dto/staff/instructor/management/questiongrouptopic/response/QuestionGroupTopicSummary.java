package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.UserQuestionGroupTopic;

import java.util.UUID;

public class QuestionGroupTopicSummary {
    @JsonView(Views.General.class)
    private final UUID id;

    @JsonView(Views.General.class)
    private final String title;

    public QuestionGroupTopicSummary(UserQuestionGroupTopic topic) {
        this(topic.getId(), topic.getTitle());
    }

    @JsonCreator
    protected QuestionGroupTopicSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title
    ) {
        this.id = id;
        this.title = title;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }
}
