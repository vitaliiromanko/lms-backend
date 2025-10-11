package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.enums.content.quiz.AnswerStatus;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import com.portfolio.lmsbackend.service.application.answer.AnswerHandlerRegistry;
import com.portfolio.lmsbackend.service.application.general.AttemptFinishingService;
import com.portfolio.lmsbackend.service.application.helper.AttemptServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.ATTEMPT_FINISHED_MESSAGE;
import static com.portfolio.lmsbackend.utils.StringsHelper.ATTEMPT_QUEUE_TEMPLATE;

@Service
@RequiredArgsConstructor
public class AttemptFinishingServiceImpl implements AttemptFinishingService {
    private final AttemptRepository attemptRepository;
    private final AttemptServiceHelper attemptServiceHelper;
    private final AnswerHandlerRegistry answerHandlerRegistry;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    @Transactional
    public void finishAttempt(UUID attemptId) {
        finishAttempt(attemptServiceHelper.findByIdOrThrow(attemptId));
    }

    @Override
    @Transactional
    public void finishAttempt(Attempt attempt) {
        if (attempt.getStatus() != AttemptStatus.STARTED) {
            return;
        }

        attempt.setFinishedAt(LocalDateTime.now());

        attempt.getAnswers().forEach(answer ->
                answerHandlerRegistry.getHandler(answer.getQuizQuestion().getQuestion().getType())
                        .handle(answer));

        boolean allGraded = attempt.getAnswers()
                .stream()
                .allMatch(answer -> answer.getStatus() == AnswerStatus.GRADED);

        if (allGraded) {
            attempt.setStatus(AttemptStatus.GRADED);
        } else {
            attempt.setStatus(AttemptStatus.PENDING_GRADING);
        }

        attemptRepository.save(attempt);

        notifyAttemptFinished(attempt.getUser().getId(), attempt.getId());
    }

    private void notifyAttemptFinished(UUID userId, UUID attemptId) {
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                ATTEMPT_QUEUE_TEMPLATE.formatted(attemptId),
                ATTEMPT_FINISHED_MESSAGE
        );
    }
}
