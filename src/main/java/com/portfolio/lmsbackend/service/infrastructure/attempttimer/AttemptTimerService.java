package com.portfolio.lmsbackend.service.infrastructure.attempttimer;

import java.time.Instant;
import java.util.UUID;

public interface AttemptTimerService {
    void scheduleAttemptFinishing(UUID attemptId, Instant finishTime);

    void cancelAttemptFinishing(UUID attemptId);

    boolean isTimerScheduled(UUID attemptId);
}
