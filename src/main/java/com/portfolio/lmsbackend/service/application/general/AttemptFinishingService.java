package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.util.UUID;

public interface AttemptFinishingService {
    void finishAttempt(UUID attemptId);

    void finishAttempt(Attempt attempt);
}
