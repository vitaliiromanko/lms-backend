package com.portfolio.lmsbackend.service.application.general;

import java.util.UUID;

public interface AttemptWebSocketService {
    boolean getAttemptTimerExists(UUID userId, UUID attemptId);
}
