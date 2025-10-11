package com.portfolio.lmsbackend.dto.general.attempt.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptDtoStatus;
import com.portfolio.lmsbackend.enums.content.quiz.GradedQuestionProgressStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.portfolio.lmsbackend.enums.content.quiz.AttemptDtoStatus.GRADED;
import static com.portfolio.lmsbackend.enums.content.quiz.GradedQuestionProgressStatus.*;

public class GetGradedAttemptResponse extends GetAttemptResponse {
    private final List<QuestionProgressForGradedAttempt> questionProgressList;
    private final LocalDateTime finishedAt;
    private final Double totalScore;
    private final Double maxScore;
    private final GetGradedAttemptAnswerResponse currentAnswer;

    public GetGradedAttemptResponse(Attempt attempt, Integer attemptNumber, Integer answerPosition) {
        super(attempt, attemptNumber, GRADED);

        AtomicInteger index = new AtomicInteger(0);
        this.questionProgressList = attempt.getAnswers().stream()
                .map(a -> new QuestionProgressForGradedAttempt(
                        index.getAndIncrement(), getGradedQuestionProgressStatus(a)))
                .toList();

        this.finishedAt = attempt.getFinishedAt();

        this.totalScore = attempt.getAnswers().stream()
                .mapToDouble(Answer::getScore)
                .sum();
        this.maxScore = attempt.getAnswers().stream()
                .mapToDouble(a -> a.getQuizQuestion().getMaxScore())
                .sum();

        this.currentAnswer = new GetGradedAttemptAnswerResponse(attempt.getAnswers().get(answerPosition));
    }

    @JsonCreator
    protected GetGradedAttemptResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("attempt_number") Integer attemptNumber,
            @JsonProperty("status") AttemptDtoStatus status,
            @JsonProperty("quiz_id") UUID quizId,
            @JsonProperty("question_progress_list") List<QuestionProgressForGradedAttempt> questionProgressList,
            @JsonProperty("started_at") LocalDateTime startedAt,
            @JsonProperty("finished_at") LocalDateTime finishedAt,
            @JsonProperty("total_score") Double totalScore,
            @JsonProperty("max_score") Double maxScore,
            @JsonProperty("current_answer") GetGradedAttemptAnswerResponse currentAnswer
    ) {
        super(id, attemptNumber, status, quizId, startedAt);
        this.questionProgressList = questionProgressList;
        this.finishedAt = finishedAt;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.currentAnswer = currentAnswer;
    }

    private GradedQuestionProgressStatus getGradedQuestionProgressStatus(Answer answer) {
        Double totalScore = answer.getScore();
        Double maxScore = answer.getQuizQuestion().getMaxScore();
        return totalScore.equals(0.0) ? INCORRECT
                : totalScore.equals(maxScore) ? FULLY_CORRECT
                : PARTIALLY_CORRECT;
    }

    @JsonProperty("question_progress_list")
    public List<QuestionProgressForGradedAttempt> questionProgressList() {
        return questionProgressList;
    }

    @JsonProperty("finished_at")
    public LocalDateTime finishedAt() {
        return finishedAt;
    }

    @JsonProperty("total_score")
    public Double totalScore() {
        return totalScore;
    }

    @JsonProperty("max_score")
    public Double maxScore() {
        return maxScore;
    }

    @JsonProperty("current_answer")
    public GetGradedAttemptAnswerResponse currentAnswer() {
        return currentAnswer;
    }
}
