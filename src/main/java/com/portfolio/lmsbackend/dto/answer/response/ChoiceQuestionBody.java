package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.content.quiz.answer.ChoiceAnswer;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceQuestion;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChoiceQuestionBody extends QuestionBody {
    private final String text;
    private final List<ChoiceOptionBody> options;

    public ChoiceQuestionBody(ChoiceAnswer choiceAnswer) {
        super(choiceAnswer);

        ChoiceQuestion choiceQuestion = (ChoiceQuestion) choiceAnswer.getQuizQuestion().getQuestion();

        this.text = choiceQuestion.getText();

        List<ChoiceOptionBody> options = choiceQuestion.getOptions().stream()
                .map(ChoiceOptionBody::new)
                .collect(Collectors.toList());

        if (choiceAnswer.getShuffleSeed() != null) {
            Collections.shuffle(options, new Random(choiceAnswer.getShuffleSeed()));
        }

        this.options = options;
    }

    @JsonCreator
    protected ChoiceQuestionBody(
            @JsonProperty("text") String text,
            @JsonProperty("options") List<ChoiceOptionBody> options,
            @JsonProperty("images") List<QuestionImageBody> images
    ) {
        super(images);
        this.text = text;
        this.options = options;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }

    @JsonProperty("options")
    public List<ChoiceOptionBody> options() {
        return options;
    }
}
