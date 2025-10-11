package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.attempt.request.FinishAttemptRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.ResetAnswerRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.StartAttemptRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.SubmitAnswerRequest;
import com.portfolio.lmsbackend.dto.general.attempt.response.GetAttemptAnswerResponse;
import com.portfolio.lmsbackend.dto.general.attempt.response.GetAttemptResponse;
import com.portfolio.lmsbackend.dto.general.attempt.response.StartAttemptResponse;

import java.util.UUID;

public interface AttemptService {
    StartAttemptResponse startAttempt(UUID userId, StartAttemptRequest startAttemptRequest);

    GetAttemptResponse getAttempt(UUID attemptId, Integer answerPosition);

    GetAttemptAnswerResponse getAttemptAnswer(UUID attemptId, Integer answerPosition);

    void submitAnswer(SubmitAnswerRequest submitAnswerRequest);

    void resetAnswer(ResetAnswerRequest resetAnswerRequest);

    void finishAttempt(FinishAttemptRequest finishAttemptRequest);
}
