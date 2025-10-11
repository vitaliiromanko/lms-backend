package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.util.List;

public abstract class QuestionBody {
    private final List<QuestionImageBody> images;

    protected QuestionBody(Answer answer) {
        this(
                answer.getQuizQuestion().getQuestion().getImages().stream()
                        .map(QuestionImageBody::new)
                        .toList()
        );
    }

    @JsonCreator
    protected QuestionBody(
            @JsonProperty("images") List<QuestionImageBody> images
    ) {
        this.images = images;
    }

    @JsonProperty("images")
    public List<QuestionImageBody> images() {
        return images;
    }
}
