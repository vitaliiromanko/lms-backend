package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.model.content.quiz.question.FillTheGapsQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.MultipleChoiceQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.SingleChoiceQuestion;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.portfolio.lmsbackend.service.application.helper.QuestionServiceHelper.unexpectedQuestionType;

public class AnswerBodyWithCorrectAnswerThatExists extends AnswerBodyWithCorrectAnswer {
    private final AnswerContent correctAnswer;

    public AnswerBodyWithCorrectAnswerThatExists(Answer answer) {
        super(answer);
        this.correctAnswer = toCorrectAnswer(answer);
    }

    @JsonCreator
    protected AnswerBodyWithCorrectAnswerThatExists(
            @JsonProperty("question_type") QuestionType questionType,
            @JsonProperty("question_body") QuestionBody questionBody,
            @JsonProperty("user_answer") AnswerContent userAnswer,
            @JsonProperty("correct_answer") AnswerContent correctAnswer
    ) {
        super(questionType, questionBody, userAnswer);
        this.correctAnswer = correctAnswer;
    }

    private static AnswerContent toCorrectAnswer(Answer answer) {
        return switch (answer.getQuizQuestion().getQuestion().getType()) {
            case SINGLE_CHOICE -> {
                SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) answer.getQuizQuestion().getQuestion();
                yield new SingleChoiceAnswerContent(getOnlyCorrectOptionId(singleChoiceQuestion));
            }
            case MULTIPLE_CHOICE -> {
                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) answer.getQuizQuestion().getQuestion();
                yield new MultipleChoiceAnswerContent(getOnlyCorrectOptionIds(multipleChoiceQuestion));
            }
            case FILL_THE_GAPS -> {
                FillTheGapsQuestion fillTheGapsQuestion = (FillTheGapsQuestion) answer.getQuizQuestion().getQuestion();
                yield new FillTheGapsAnswerContent(getOnlyCorrectGapAnswerContent(fillTheGapsQuestion));
            }
            default -> throw unexpectedQuestionType(answer.getQuizQuestion().getQuestion());
        };
    }

    private static UUID getOnlyCorrectOptionId(SingleChoiceQuestion question) {
        return question.getOptions().stream()
                .filter(ChoiceOption::getCorrect)
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple correct options found");
                })
                .orElseThrow(() -> new IllegalStateException("No correct option found"))
                .getId();
    }

    private static Set<UUID> getOnlyCorrectOptionIds(MultipleChoiceQuestion question) {
        Set<UUID> correctOptionIds = question.getOptions().stream()
                .filter(ChoiceOption::getCorrect)
                .map(ChoiceOption::getId)
                .collect(Collectors.toSet());

        if (correctOptionIds.isEmpty()) {
            throw new IllegalStateException("No correct option found");
        }

        return correctOptionIds;
    }

    private static Set<GapAnswerContent> getOnlyCorrectGapAnswerContent(FillTheGapsQuestion question) {
        Set<GapAnswerContent> gapAnswerContents = question.getMissingTextSegments().stream()
                .map(m -> new GapAnswerContent(m.getId(), m.getText()))
                .collect(Collectors.toSet());

        if (gapAnswerContents.isEmpty()) {
            throw new IllegalStateException("No correct gap answer content found");
        }

        return gapAnswerContents;
    }

    @JsonProperty("correct_answer")
    public AnswerContent correctAnswer() {
        return correctAnswer;
    }
}
