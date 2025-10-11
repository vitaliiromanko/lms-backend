package com.portfolio.lmsbackend.security.preauthorize;

import com.portfolio.lmsbackend.dto.general.attempt.request.FinishAttemptRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.ResetAnswerRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.StartAttemptRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.SubmitAnswerRequest;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupTimeWindowAccessRestriction;
import com.portfolio.lmsbackend.repository.course.AnswerRepository;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import com.portfolio.lmsbackend.repository.course.QuizGroupAccessRestrictionRepository;
import com.portfolio.lmsbackend.repository.course.QuizGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus.PENDING_GRADING;
import static com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus.STARTED;
import static com.portfolio.lmsbackend.security.preauthorize.SecurityHelper.isStudent;

@Component
@RequiredArgsConstructor
public class AttemptSecurity {
    private final QuizGroupRepository quizGroupRepository;
    private final QuizGroupAccessRestrictionRepository quizGroupAccessRestrictionRepository;
    private final AttemptRepository attemptRepository;
    private final AnswerRepository answerRepository;
    private final SectionContentSecurity sectionContentSecurity;

    public boolean canStartAttempt(Authentication authentication, StartAttemptRequest startAttemptRequest) {
        UUID quizGroupId = startAttemptRequest.quizGroupId();
        return hasGeneralAccessToStart(authentication, quizGroupId)
                && hasAttemptsLeft(quizGroupId, getUserId(authentication));
    }

    public boolean canGetAttempt(Authentication authentication, UUID attemptId) {
        return canAccessAttemptAndAnswers(authentication, attemptId);
    }

    public boolean canGetAttemptAnswer(Authentication authentication, UUID attemptId) {
        return canAccessAttemptAndAnswers(authentication, attemptId);
    }

    public boolean canSubmitAnswer(Authentication authentication, SubmitAnswerRequest submitAnswerRequest) {
        return canUpdateAnswer(authentication, submitAnswerRequest.answerId());
    }

    public boolean canResetAnswer(Authentication authentication, ResetAnswerRequest resetAnswerRequest) {
        return canUpdateAnswer(authentication, resetAnswerRequest.answerId());
    }

    public boolean canFinishAttempt(Authentication authentication, FinishAttemptRequest finishAttemptRequest) {
        UUID attemptId = finishAttemptRequest.attemptId();
        return hasGeneralAccess(authentication, attemptId)
                && belongsToUserAndStarted(attemptId, getUserId(authentication));
    }

    private boolean canAccessAttemptAndAnswers(Authentication authentication, UUID attemptId) {
        return hasGeneralAccess(authentication, attemptId)
                && belongsToUserAndNotPendingGrading(attemptId, getUserId(authentication));
    }

    private boolean canUpdateAnswer(Authentication authentication, UUID answerId) {
        return answerRepository.findAttemptIdByAnswerId(answerId)
                .map(attemptId -> hasGeneralAccess(authentication, attemptId)
                        && belongsToUserAndStarted(attemptId, getUserId(authentication)))
                .orElse(false);

    }

    private boolean hasGeneralAccessToStart(Authentication authentication, UUID quizGroupId) {
        return !isStudent(authentication) || canStudentAccessToStart(authentication, quizGroupId);
    }

    private boolean canStudentAccessToStart(Authentication authentication, UUID quizGroupId) {
        return sectionContentSecurity.canStudentAccess(authentication, quizGroupId)
                && !hasAccessRestriction(quizGroupId, getUserId(authentication));
    }

    private boolean hasAccessRestriction(UUID quizGroupId, UUID studentId) {
        Optional<QuizGroupAccessRestriction> accessRestrictionOpt = quizGroupAccessRestrictionRepository
                .findByGroupIdAndStudentId(quizGroupId, studentId);

        if (accessRestrictionOpt.isEmpty()) {
            return false;
        }

        QuizGroupAccessRestriction accessRestriction = accessRestrictionOpt.get();
        return switch (accessRestriction.getType()) {
            case BLOCKED -> true;
            case TIME_WINDOW -> {
                QuizGroupTimeWindowAccessRestriction timeWindowAccessRestriction =
                        (QuizGroupTimeWindowAccessRestriction) accessRestriction;
                LocalDateTime now = LocalDateTime.now();

                yield now.isBefore(timeWindowAccessRestriction.getAvailableFrom())
                        || now.isAfter(timeWindowAccessRestriction.getAvailableUntil());
            }
        };
    }

    private boolean hasGeneralAccess(Authentication authentication, UUID attemptId) {
        return !isStudent(authentication) || attemptRepository.findQuizGroupIdByAttemptId(attemptId)
                .map(quizGroupId -> sectionContentSecurity.canStudentAccess(authentication, quizGroupId))
                .orElse(false);
    }


    private boolean hasAttemptsLeft(UUID quizGroupId, UUID userId) {
        Integer maxAttempts = quizGroupRepository.findMaxAttemptsById(quizGroupId);

        if (maxAttempts == null) {
            return true;
        }

        long usedAttempts = attemptRepository.countByQuizGroupIdAndUserId(quizGroupId, userId);
        return usedAttempts < maxAttempts;
    }

    private boolean belongsToUserAndNotPendingGrading(UUID attemptId, UUID userId) {
        return attemptRepository.existsByIdAndUserIdAndStatusNot(attemptId, userId, PENDING_GRADING);
    }

    private boolean belongsToUserAndStarted(UUID attemptId, UUID userId) {
        return attemptRepository.existsByIdAndUserIdAndStatus(attemptId, userId, STARTED);
    }

    private UUID getUserId(Authentication authentication) {
        return UUID.fromString(authentication.getName());
    }
}
