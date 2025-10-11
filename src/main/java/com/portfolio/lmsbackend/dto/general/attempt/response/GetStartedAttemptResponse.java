package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptDtoStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.portfolio.lmsbackend.enums.content.quiz.AnswerStatus.NOT_ANSWERED;
import static com.portfolio.lmsbackend.enums.content.quiz.AttemptDtoStatus.STARTED;

public class GetStartedAttemptResponse extends GetAttemptResponse {
    private final List<QuestionProgressForStartedAttempt> questionProgressList;
    private final LocalDateTime expectedFinishTime;
    private final GetStartedAttemptAnswerResponse currentAnswer;

    public GetStartedAttemptResponse(Attempt attempt, Integer attemptNumber, Integer answerPosition) {
        super(attempt, attemptNumber, STARTED);

        AtomicInteger index = new AtomicInteger(0);
        this.questionProgressList = attempt.getAnswers().stream()
                .map(a -> new QuestionProgressForStartedAttempt(index.getAndIncrement(), getAnswered(a)))
                .toList();

        Duration duration = attempt.getQuiz().getGroup().getDuration();
        this.expectedFinishTime = duration != null
                ? attempt.getStartedAt().plus(duration)
                : null;

        this.currentAnswer = new GetStartedAttemptAnswerResponse(attempt.getAnswers().get(answerPosition));
    }

    @JsonCreator
    protected GetStartedAttemptResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("attempt_number") Integer attemptNumber,
            @JsonProperty("status") AttemptDtoStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("question_progress_list") List<QuestionProgressForStartedAttempt> questionProgressList,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("expected_finish_time") LocalDateTime expectedFinishTime,
            @JsonProperty("current_answer") GetStartedAttemptAnswerResponse currentAnswer
    ) {
        super(id, attemptNumber, status, quizId, startedAt);
        this.questionProgressList = questionProgressList;
        this.expectedFinishTime = expectedFinishTime;
        this.currentAnswer = currentAnswer;
    }

    private static Boolean getAnswered(Answer answer) {
        return answer.getStatus() != NOT_ANSWERED;
    }

    @JsonProperty("question_progress_list")
    public List<QuestionProgressForStartedAttempt> questionProgressList() {
        return questionProgressList;
    }

    @JsonProperty("expected_finish_time")
    public LocalDateTime expectedFinishTime() {
        return expectedFinishTime;
    }

    @JsonProperty("current_answer")
    public GetStartedAttemptAnswerResponse currentAnswer() {
        return currentAnswer;
    }
}
