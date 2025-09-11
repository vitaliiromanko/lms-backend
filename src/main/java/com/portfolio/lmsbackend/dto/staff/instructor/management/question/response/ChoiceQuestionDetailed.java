package com.portfolio.lmsbackend.dto.staff.instructor.management.question.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.CreatedByResponse;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceQuestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ChoiceQuestionDetailed extends QuestionDetailed {
    @JsonView(Views.General.class)
    private final String text;

    @JsonView(Views.General.class)
    private final List<ChoiceOptionSummary> options;

    @JsonView(Views.General.class)
    private final Boolean shuffleOptions;

    public ChoiceQuestionDetailed(ChoiceQuestion choiceQuestion) {
        super(choiceQuestion);
        this.text = choiceQuestion.getText();
        this.options = choiceQuestion.getOptions().stream()
                .map(ChoiceOptionSummary::new)
                .toList();
        this.shuffleOptions = choiceQuestion.getShuffleOptions();
    }

    @JsonCreator
    protected ChoiceQuestionDetailed(
            @JsonProperty("id") UUID id,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("text") String text,
            @JsonProperty("options") List<ChoiceOptionSummary> options,
            @JsonProperty("shuffle_options") Boolean shuffleOptions,
            @JsonProperty("images") List<QuestionImageSummary> images,
            @JsonProperty("created_by") CreatedByResponse createdBy,
            @JsonProperty("created_at") LocalDateTime createdAt
    ) {
        super(id, type, images, createdBy, createdAt);
        this.text = text;
        this.options = options;
        this.shuffleOptions = shuffleOptions;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }

    @JsonProperty("options")
    public List<ChoiceOptionSummary> options() {
        return options;
    }

    @JsonProperty("shuffle_options")
    public Boolean shuffleOptions() {
        return shuffleOptions;
    }
}
