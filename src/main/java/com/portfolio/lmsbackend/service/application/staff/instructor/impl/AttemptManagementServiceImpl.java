package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.FinalizeAttemptGradingRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.GetFinishedAttemptsByQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.GradeFinishedAttemptAnswerRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response.GetFinishedAttemptResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response.GetFinishedAttemptsByQuizGroupResponse;
import com.portfolio.lmsbackend.exception.course.AttemptAlreadyGradedException;
import com.portfolio.lmsbackend.exception.course.AttemptNotFinishedException;
import com.portfolio.lmsbackend.exception.course.ScoreExceedsMaximumException;
import com.portfolio.lmsbackend.exception.course.UnfinishedGradingException;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.repository.course.AnswerRepository;
import com.portfolio.lmsbackend.repository.course.AttemptCriteriaRepository;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import com.portfolio.lmsbackend.service.application.helper.AnswerServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.AttemptServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.AttemptManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus.GRADED;
import static com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus.STARTED;

@Service
@RequiredArgsConstructor
public class AttemptManagementServiceImpl implements AttemptManagementService {
    private final AttemptRepository attemptRepository;
    private final AnswerRepository answerRepository;
    private final AttemptCriteriaRepository attemptCriteriaRepository;
    private final AttemptServiceHelper attemptServiceHelper;
    private final AnswerServiceHelper answerServiceHelper;

    @Override
    public Page<GetFinishedAttemptsByQuizGroupResponse> getFinishedAttemptsByQuizGroup(
            GetFinishedAttemptsByQuizGroupRequest getFinishedAttemptsByQuizGroupRequest) {
        return attemptCriteriaRepository.findByCriteria(getFinishedAttemptsByQuizGroupRequest)
                .map(GetFinishedAttemptsByQuizGroupResponse::new);
    }

    @Override
    @Transactional
    public GetFinishedAttemptResponse getFinishedAttempt(UUID attemptId) {
        Attempt attempt = attemptServiceHelper.findByIdOrThrow(attemptId);
        return new GetFinishedAttemptResponse(attempt, attemptServiceHelper.getAttemptNumber(attempt));
    }

    @Override
    @Transactional
    public void gradeFinishedAttemptAnswer(GradeFinishedAttemptAnswerRequest gradeFinishedAttemptAnswerRequest) {
        Answer answer = answerServiceHelper.findByIdOrThrow(gradeFinishedAttemptAnswerRequest.answerId());

        if (answer.getAttempt().getStatus() == STARTED) {
            throw new AttemptNotFinishedException();
        }

        if (answer.getQuizQuestion().getMaxScore() < gradeFinishedAttemptAnswerRequest.score()) {
            throw new ScoreExceedsMaximumException();
        }

        answer.setScore(gradeFinishedAttemptAnswerRequest.score());
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void finalizeAttemptGrading(FinalizeAttemptGradingRequest finalizeAttemptGradingRequest) {
        Attempt attempt = attemptServiceHelper.findByIdOrThrow(finalizeAttemptGradingRequest.attemptId());

        if (attempt.getStatus() == STARTED) {
            throw new AttemptNotFinishedException();
        }

        if (attempt.getStatus() == GRADED) {
            throw new AttemptAlreadyGradedException();
        }

        boolean allGraded = attempt.getAnswers()
                .stream()
                .allMatch(answer -> answer.getScore() != null);

        if (!allGraded) {
            throw new UnfinishedGradingException();
        }

        attempt.setStatus(GRADED);
        attemptRepository.save(attempt);
    }
}
