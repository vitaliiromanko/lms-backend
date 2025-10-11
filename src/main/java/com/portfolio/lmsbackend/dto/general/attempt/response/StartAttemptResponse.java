package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptDtoStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class StartAttemptResponse extends GetStartedAttemptResponse {
    private static final Integer FIRST_ANSWER_POSITION = 0;

    public StartAttemptResponse(Attempt attempt, Integer attemptNumber) {
        super(attempt, attemptNumber, FIRST_ANSWER_POSITION);
    }

    @JsonCreator
    protected StartAttemptResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("attempt_number") Integer attemptNumber,
            @JsonProperty("status") AttemptDtoStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("question_progress_list") List<QuestionProgressForStartedAttempt> questionProgressList,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("expected_finish_time") LocalDateTime expectedFinishTime,
            @JsonProperty("current_answer") GetStartedAttemptAnswerResponse currentAnswer
    ) {
        super(id, attemptNumber, status, quizId, questionProgressList, startedAt, expectedFinishTime, currentAnswer);
    }
}
