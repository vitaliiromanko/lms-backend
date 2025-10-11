package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.content.quiz.answer.TextLongAnswer;
import com.portfolio.lmsbackend.model.content.quiz.question.TextLongQuestion;

import java.util.List;

public class TextLongQuestionBody extends QuestionBody {
    private final String text;

    public TextLongQuestionBody(TextLongAnswer textLongAnswer) {
        super(textLongAnswer);
        this.text = ((TextLongQuestion) textLongAnswer.getQuizQuestion().getQuestion()).getText();
    }

    @JsonCreator
    protected TextLongQuestionBody(
            @JsonProperty("text") String text,
            @JsonProperty("images") List<QuestionImageBody> images
    ) {
        super(images);
        this.text = text;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }
}
