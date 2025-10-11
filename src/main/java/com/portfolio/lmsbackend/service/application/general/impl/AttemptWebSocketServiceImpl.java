package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.service.application.general.AttemptWebSocketService;
import com.portfolio.lmsbackend.service.infrastructure.attempttimer.AttemptTimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttemptWebSocketServiceImpl implements AttemptWebSocketService {
    private final AttemptTimerService attemptTimerService;

    @Override
    public boolean getAttemptTimerExists(UUID userId, UUID attemptId) {
        return attemptTimerService.isTimerScheduled(attemptId);
    }
}
