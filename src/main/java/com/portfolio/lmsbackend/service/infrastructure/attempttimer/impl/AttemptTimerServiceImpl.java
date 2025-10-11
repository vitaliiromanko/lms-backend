package com.portfolio.lmsbackend.service.infrastructure.attempttimer.impl;

import com.portfolio.lmsbackend.service.application.general.AttemptFinishingService;
import com.portfolio.lmsbackend.service.infrastructure.attempttimer.AttemptTimerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class AttemptTimerServiceImpl implements AttemptTimerService {
    private final TaskScheduler taskScheduler;
    private final AttemptFinishingService attemptFinishingService;

    private final Map<UUID, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public AttemptTimerServiceImpl(
            @Qualifier("attemptTaskScheduler") TaskScheduler taskScheduler,
            AttemptFinishingService attemptFinishingService
    ) {
        this.taskScheduler = taskScheduler;
        this.attemptFinishingService = attemptFinishingService;
    }

    @Override
    public void scheduleAttemptFinishing(UUID attemptId, Instant finishTime) {
        cancelAttemptFinishing(attemptId);

        ScheduledFuture<?> future = taskScheduler.schedule(
                () -> attemptFinishingService.finishAttempt(attemptId),
                finishTime
        );

        scheduledTasks.put(attemptId, future);
    }

    @Override
    public void cancelAttemptFinishing(UUID attemptId) {
        ScheduledFuture<?> future = scheduledTasks.remove(attemptId);
        if (future != null) {
            future.cancel(false);
        }
    }

    @Override
    public boolean isTimerScheduled(UUID attemptId) {
        ScheduledFuture<?> future = scheduledTasks.get(attemptId);
        return future != null && !future.isDone();
    }
}
