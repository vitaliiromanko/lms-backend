package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.FinalizeAttemptGradingRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.GetFinishedAttemptsByQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.GradeFinishedAttemptAnswerRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response.GetFinishedAttemptResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.response.GetFinishedAttemptsByQuizGroupResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AttemptManagementService {
    Page<GetFinishedAttemptsByQuizGroupResponse> getFinishedAttemptsByQuizGroup(GetFinishedAttemptsByQuizGroupRequest getFinishedAttemptsByQuizGroupRequest);

    GetFinishedAttemptResponse getFinishedAttempt(UUID attemptId);

    void gradeFinishedAttemptAnswer(GradeFinishedAttemptAnswerRequest gradeFinishedAttemptAnswerRequest);

    void finalizeAttemptGrading(FinalizeAttemptGradingRequest finalizeAttemptGradingRequest);
}
