package com.portfolio.lmsbackend.config.websocket;

import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.PathContainer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Objects;
import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.*;

@Component
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {
    private final AttemptRepository attemptRepository;

    private static final PathPattern ATTEMPT_DESTINATION_PATTERN =
            PathPatternParser.defaultInstance.parse(WS_USER_PREFIX + ATTEMPT_QUEUE_PREFIX + "/{id}");

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();
        if (command == null) {
            throw new MessageDeliveryException("Unknown STOMP command");
        }

        switch (command) {
            case CONNECT:
            case DISCONNECT:
            case UNSUBSCRIBE:
            case SEND:
                break;
            case SUBSCRIBE:
                checkSubscribeAccess(accessor);
                break;
            default:
                throw new MessageDeliveryException("STOMP command " + command + " is forbidden");
        }

        return message;
    }

    private void checkSubscribeAccess(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        if (destination == null) {
            throw new MessageDeliveryException("Subscription without destination is forbidden");
        }

        if (destination.equals(WS_USER_PREFIX + ERRORS_QUEUE_PREFIX)) {
            return;
        }

        PathContainer path = PathContainer.parsePath(destination);
        PathPattern.PathMatchInfo matchInfo = ATTEMPT_DESTINATION_PATTERN.matchAndExtract(path);

        if (matchInfo == null) {
            throw new MessageDeliveryException("Subscription to this topic is forbidden");
        }

        String attemptIdStr = matchInfo.getUriVariables().get("id");
        String username = Objects.requireNonNull(accessor.getUser()).getName();

        if (!userOwnsAttempt(username, attemptIdStr)) {
            throw new MessageDeliveryException("You do not have access to this attempt");
        }
    }

    private boolean userOwnsAttempt(String username, String attemptIdStr) {
        UUID userId = UUID.fromString(username);
        UUID attemptId;
        try {
            attemptId = UUID.fromString(attemptIdStr);
        } catch (IllegalArgumentException e) {
            throw new MessageDeliveryException("Attempt ID is not a valid UUID");
        }

        return attemptRepository.existsByIdAndUserId(attemptId, userId);
    }
}
