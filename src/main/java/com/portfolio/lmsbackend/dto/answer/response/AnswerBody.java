package com.portfolio.lmsbackend.dto.answer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.*;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;

import java.util.stream.Collectors;

public abstract class AnswerBody {
    private final QuestionType questionType;
    private final QuestionBody questionBody;
    private final AnswerContent userAnswer;

    protected AnswerBody(Answer answer) {
        this(
                answer.getQuizQuestion().getQuestion().getType(),
                toQuestionBody(answer),
                toUserAnswer(answer)
        );
    }

    @JsonCreator
    protected AnswerBody(
            @JsonProperty("question_type") QuestionType questionType,
            @JsonProperty("question_body") QuestionBody questionBody,
            @JsonProperty("user_answer") AnswerContent userAnswer
    ) {
        this.questionType = questionType;
        this.questionBody = questionBody;
        this.userAnswer = userAnswer;
    }

    private static QuestionBody toQuestionBody(Answer answer) {
        return switch (answer.getQuizQuestion().getQuestion().getType()) {
            case SINGLE_CHOICE, MULTIPLE_CHOICE -> new ChoiceQuestionBody((ChoiceAnswer) answer);
            case FILL_THE_GAPS -> new FillTheGapsQuestionBody((FillTheGapsAnswer) answer);
            case TEXT_LONG -> new TextLongQuestionBody((TextLongAnswer) answer);
        };
    }

    private static AnswerContent toUserAnswer(Answer answer) {
        return !answer.getAnswered()
                ? null
                : switch (answer.getQuizQuestion().getQuestion().getType()) {
            case SINGLE_CHOICE -> {
                SingleChoiceAnswer singleChoiceAnswer = (SingleChoiceAnswer) answer;
                yield new SingleChoiceAnswerContent(singleChoiceAnswer.getSelectedOption().getId());
            }
            case MULTIPLE_CHOICE -> {
                MultipleChoiceAnswer multipleChoiceAnswer = (MultipleChoiceAnswer) answer;
                yield new MultipleChoiceAnswerContent(multipleChoiceAnswer.getSelectedOptions().stream()
                        .map(ChoiceOption::getId)
                        .collect(Collectors.toSet()));
            }
            case FILL_THE_GAPS -> {
                FillTheGapsAnswer fillTheGapsAnswer = (FillTheGapsAnswer) answer;
                yield new FillTheGapsAnswerContent(fillTheGapsAnswer.getGapAnswerSegments().stream()
                        .map(g -> new GapAnswerContent(g.getMissingTextSegment().getId(), g.getText()))
                        .collect(Collectors.toSet()));
            }
            case TEXT_LONG -> {
                TextLongAnswer textLongAnswer = (TextLongAnswer) answer;
                yield new TextLongAnswerContent(textLongAnswer.getText());
            }
        };
    }

    @JsonProperty("question_type")
    public QuestionType questionType() {
        return questionType;
    }

    @JsonProperty("question_body")
    public QuestionBody questionBody() {
        return questionBody;
    }

    @JsonProperty("user_answer")
    public AnswerContent userAnswer() {
        return userAnswer;
    }
}
