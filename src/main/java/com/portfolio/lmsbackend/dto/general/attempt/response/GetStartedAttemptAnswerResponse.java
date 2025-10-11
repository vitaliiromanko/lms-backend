package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AnswerDtoStatus;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.quiz.AnswerDtoStatus.STARTED;

public class GetStartedAttemptAnswerResponse extends GetAttemptAnswerResponse {
    private final AnswerBodyWithoutCorrectAnswer answerBody;

    public GetStartedAttemptAnswerResponse(Answer answer) {
        super(answer, STARTED);
        this.answerBody = new AnswerBodyWithoutCorrectAnswer(answer);
    }

    @JsonCreator
    protected GetStartedAttemptAnswerResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("status") AnswerDtoStatus status,
            @JsonProperty("answer_body") AnswerBodyWithoutCorrectAnswer answerBody
    ) {
        super(id, status);
        this.answerBody = answerBody;
    }

    @JsonProperty("answer_body")
    public AnswerBodyWithoutCorrectAnswer answerBody() {
        return answerBody;
    }
}
