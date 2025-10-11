package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AnswerDtoStatus;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.quiz.AnswerDtoStatus.GRADED;

public class GetGradedAttemptAnswerResponse extends GetAttemptAnswerResponse {
    private final AnswerBodyWithCorrectAnswer answerBody;
    private final Double score;
    private final Double maxScore;

    public GetGradedAttemptAnswerResponse(Answer answer) {
        super(answer, GRADED);
        this.answerBody = getAnswerBodyWithCorrectAnswer(answer);
        this.score = answer.getScore();
        this.maxScore = answer.getQuizQuestion().getMaxScore();
    }

    @JsonCreator
    protected GetGradedAttemptAnswerResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") AnswerDtoStatus status,
            @JsonProperty("answer_body") AnswerBodyWithCorrectAnswer answerBody,
            @JsonProperty("score") Double score,
            @JsonProperty("max_score") Double maxScore
    ) {
        super(id, status);
        this.answerBody = answerBody;
        this.score = score;
        this.maxScore = maxScore;
    }

    private static AnswerBodyWithCorrectAnswer getAnswerBodyWithCorrectAnswer(Answer answer) {
        return switch (answer.getQuizQuestion().getQuestion().getType()) {
            case SINGLE_CHOICE, MULTIPLE_CHOICE, FILL_THE_GAPS -> new AnswerBodyWithCorrectAnswerThatExists(answer);
            case TEXT_LONG -> new AnswerBodyWithCorrectAnswerThatNotExist(answer);
        };
    }

    @JsonProperty("answer_body")
    public AnswerBodyWithCorrectAnswer answerBody() {
        return answerBody;
    }

    @JsonProperty("score")
    public Double score() {
        return score;
    }

    @JsonProperty("max_score")
    public Double maxScore() {
        return maxScore;
    }
}
