package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.service.application.general.AttemptWebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.ATTEMPT_QUEUE_TEMPLATE;
import static com.portfolio.lmsbackend.utils.StringsHelper.ERRORS_QUEUE_PREFIX;

@Controller
@RequiredArgsConstructor
public class AttemptWebSocketController {
    private final AttemptWebSocketService attemptWebSocketService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/attempt/timerExists")
    public void getAttemptTimerExists(Principal principal, String attemptIdStr) {
        try {
            String cleanAttemptIdStr = attemptIdStr.trim();
            boolean timerExists = attemptWebSocketService.getAttemptTimerExists(
                    UUID.fromString(principal.getName()), UUID.fromString(cleanAttemptIdStr));

            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    ATTEMPT_QUEUE_TEMPLATE.formatted(cleanAttemptIdStr),
                    timerExists ? "Timer is running" : "No active timer"
            );
        } catch (IllegalArgumentException e) {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    ERRORS_QUEUE_PREFIX,
                    "Provided attempt ID is not a valid UUID"
            );
        }
    }
}
