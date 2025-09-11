package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.model.content.quiz.question.QuestionGroup;

import java.util.UUID;

public class QuestionGroupSummary {
    @JsonView(Views.General.class)
    private final UUID id;

    @JsonView(Views.General.class)
    private final String title;

    public QuestionGroupSummary(QuestionGroup group) {
        this(group.getId(), group.getTitle());
    }

    @JsonCreator
    protected QuestionGroupSummary(
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
