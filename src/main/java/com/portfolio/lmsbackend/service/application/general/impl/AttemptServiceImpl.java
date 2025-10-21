package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.attempt.request.*;
import com.portfolio.lmsbackend.dto.general.attempt.response.*;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.exception.InvalidPositionException;
import com.portfolio.lmsbackend.exception.course.AnswerChoiceDoesNotBelongToQuestionException;
import com.portfolio.lmsbackend.exception.course.AnswerTypeMismatchException;
import com.portfolio.lmsbackend.exception.course.AttemptPendingGradingException;
import com.portfolio.lmsbackend.exception.course.MissingTextSegmentDoesNotBelongToQuestionException;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.answer.*;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.FillTheGapsQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.MissingTextSegment;
import com.portfolio.lmsbackend.repository.course.AnswerRepository;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import com.portfolio.lmsbackend.service.application.general.AttemptFinishingService;
import com.portfolio.lmsbackend.service.application.general.AttemptService;
import com.portfolio.lmsbackend.service.application.helper.*;
import com.portfolio.lmsbackend.service.infrastructure.attempttimer.AttemptTimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {
    private final AttemptRepository attemptRepository;
    private final AnswerRepository answerRepository;
    private final UserServiceHelper userServiceHelper;
    private final QuizGroupServiceHelper quizGroupServiceHelper;
    private final AttemptServiceHelper attemptServiceHelper;
    private final AnswerServiceHelper answerServiceHelper;
    private final ChoiceOptionServiceHelper choiceOptionServiceHelper;
    private final MissingTextSegmentServiceHelper missingTextSegmentServiceHelper;
    private final AttemptFinishingService attemptFinishingService;
    private final AttemptTimerService attemptTimerService;

    @Override
    @Transactional
    public StartAttemptResponse startAttempt(UUID userId, StartAttemptRequest startAttemptRequest) {
        Attempt attempt = attemptRepository.save(new Attempt(
                quizGroupServiceHelper.findByIdOrThrow(startAttemptRequest.quizGroupId())
                        .getQuizzes().getLast(),
                userServiceHelper.findByIdOrThrow(userId)
        ));

        scheduleAttemptFinishingIfDurationPresent(attempt);

        return new StartAttemptResponse(attempt, attemptServiceHelper.getAttemptNumber(attempt));
    }

    @Override
    @Transactional
    public GetAttemptResponse getAttempt(UUID attemptId, Integer answerPosition) {
        Attempt attempt = attemptServiceHelper.findByIdOrThrow(attemptId);

        checkAnswerPosition(attempt, answerPosition);

        return switch (attempt.getStatus()) {
            case AttemptStatus.STARTED ->
                    new GetStartedAttemptResponse(attempt, attemptServiceHelper.getAttemptNumber(attempt), answerPosition);
            case AttemptStatus.PENDING_GRADING -> throw new AttemptPendingGradingException();
            case AttemptStatus.GRADED ->
                    new GetGradedAttemptResponse(attempt, attemptServiceHelper.getAttemptNumber(attempt), answerPosition);
        };
    }

    @Override
    @Transactional
    public GetAttemptAnswerResponse getAttemptAnswer(UUID attemptId, Integer answerPosition) {
        Attempt attempt = attemptServiceHelper.findByIdOrThrow(attemptId);

        checkAnswerPosition(attempt, answerPosition);

        Answer answer = attempt.getAnswers().get(answerPosition);
        return switch (attempt.getStatus()) {
            case AttemptStatus.STARTED -> new GetStartedAttemptAnswerResponse(answer);
            case AttemptStatus.PENDING_GRADING -> throw new AttemptPendingGradingException();
            case AttemptStatus.GRADED -> new GetGradedAttemptAnswerResponse(answer);
        };
    }

    @Override
    @Transactional
    public void submitAnswer(SubmitAnswerRequest submitAnswerRequest) {
        Answer answer = answerServiceHelper.findByIdOrThrow(submitAnswerRequest.answerId());

        checkAnswerTypeConsistency(answer, submitAnswerRequest.type());

        switch (answer.getQuizQuestion().getQuestion().getType()) {
            case SINGLE_CHOICE -> submitSingleChoiceAnswer((SingleChoiceAnswer) answer,
                    (SubmitSingleChoiceAnswerRequest) submitAnswerRequest);
            case MULTIPLE_CHOICE -> submitMultipleChoiceAnswer((MultipleChoiceAnswer) answer,
                    (SubmitMultipleChoiceAnswerRequest) submitAnswerRequest);
            case FILL_THE_GAPS -> submitFillTheGapsAnswer((FillTheGapsAnswer) answer,
                    (SubmitFillTheGapsAnswerRequest) submitAnswerRequest);
            case TEXT_LONG -> submitTextLongAnswer((TextLongAnswer) answer,
                    (SubmitTextLongAnswerRequest) submitAnswerRequest);
        }

        answer.setAnswered(true);
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void resetAnswer(ResetAnswerRequest resetAnswerRequest) {
        Answer answer = answerServiceHelper.findByIdOrThrow(resetAnswerRequest.answerId());

        switch (answer.getQuizQuestion().getQuestion().getType()) {
            case SINGLE_CHOICE -> resetSingleChoiceAnswer((SingleChoiceAnswer) answer);
            case MULTIPLE_CHOICE -> resetMultipleChoiceAnswer((MultipleChoiceAnswer) answer);
            case FILL_THE_GAPS -> resetFillTheGapsAnswer((FillTheGapsAnswer) answer);
            case TEXT_LONG -> resetTextLongAnswer((TextLongAnswer) answer);
        }

        answer.setAnswered(false);
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void finishAttempt(FinishAttemptRequest finishAttemptRequest) {
        Attempt attempt = attemptServiceHelper.findByIdOrThrow(finishAttemptRequest.attemptId());

        cancelAttemptFinishing(attempt);

        attemptFinishingService.finishAttempt(attempt);
    }

    private void scheduleAttemptFinishingIfDurationPresent(Attempt attempt) {
        Duration attemptDuration = attempt.getQuiz().getGroup().getDuration();
        if (attemptDuration != null) {
            Instant finishInstant = calculateFinishInstant(attempt.getStartedAt(), attemptDuration);
            attemptTimerService.scheduleAttemptFinishing(attempt.getId(), finishInstant);
        }
    }

    private void cancelAttemptFinishing(Attempt attempt) {
        attemptTimerService.cancelAttemptFinishing(attempt.getId());
    }

    private Instant calculateFinishInstant(LocalDateTime startedAt, Duration duration) {
        return startedAt.plus(duration)
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }

    private void checkAnswerPosition(Attempt attempt, Integer answerPosition) {
        if (attempt.getAnswers().size() - 1 < answerPosition) {
            throw new InvalidPositionException();
        }
    }

    private void checkAnswerTypeConsistency(Answer answer, QuestionType submitAnswerType) {
        if (answer.getQuizQuestion().getQuestion().getType() != submitAnswerType) {
            throw new AnswerTypeMismatchException();
        }
    }

    private void submitSingleChoiceAnswer(SingleChoiceAnswer answer,
                                          SubmitSingleChoiceAnswerRequest submitAnswerRequest) {
        ChoiceOption selectedOption = choiceOptionServiceHelper.findByIdOrThrow(submitAnswerRequest.selectedOptionId());

        checkAnswerChoiceOwnership((ChoiceQuestion) answer.getQuizQuestion().getQuestion(), selectedOption);

        resetSingleChoiceAnswer(answer);
        answer.setSelectedOption(selectedOption);
    }

    private void submitMultipleChoiceAnswer(MultipleChoiceAnswer answer,
                                            SubmitMultipleChoiceAnswerRequest submitAnswerRequest) {
        Set<ChoiceOption> selectedOptions = submitAnswerRequest.selectedOptionIds().stream()
                .map(choiceOptionServiceHelper::findByIdOrThrow)
                .collect(Collectors.toSet());

        ChoiceQuestion choiceQuestion = (ChoiceQuestion) answer.getQuizQuestion().getQuestion();
        selectedOptions.forEach(selectedOption ->
                checkAnswerChoiceOwnership(choiceQuestion, selectedOption));

        resetMultipleChoiceAnswer(answer);
        answer.getSelectedOptions().addAll(selectedOptions);
    }

    private void submitFillTheGapsAnswer(FillTheGapsAnswer answer, SubmitFillTheGapsAnswerRequest submitAnswerRequest) {
        Set<GapAnswerSegment> gapAnswerSegments = submitAnswerRequest.gapAnswerSegments().stream()
                .map(g -> new GapAnswerSegment(g.text(), answer,
                        missingTextSegmentServiceHelper.findByIdOrThrow(g.missingTextSegmentId())))
                .collect(Collectors.toSet());

        FillTheGapsQuestion fillTheGapsQuestion = (FillTheGapsQuestion) answer.getQuizQuestion().getQuestion();
        gapAnswerSegments.forEach(gapAnswerSegment ->
                checkMissingTextSegmentOwnership(fillTheGapsQuestion, gapAnswerSegment.getMissingTextSegment()));

        resetFillTheGapsAnswer(answer);
        answer.getGapAnswerSegments().addAll(gapAnswerSegments);
    }

    private void submitTextLongAnswer(TextLongAnswer answer, SubmitTextLongAnswerRequest submitAnswerRequest) {
        resetTextLongAnswer(answer);
        answer.setText(submitAnswerRequest.text());
    }

    private void checkAnswerChoiceOwnership(ChoiceQuestion choiceQuestion, ChoiceOption selectedOption) {
        if (!choiceQuestion.getOptions().contains(selectedOption)) {
            throw new AnswerChoiceDoesNotBelongToQuestionException();
        }
    }

    private void checkMissingTextSegmentOwnership(FillTheGapsQuestion fillTheGapsQuestion,
                                                  MissingTextSegment missingTextSegment) {
        if (!fillTheGapsQuestion.getMissingTextSegments().contains(missingTextSegment)) {
            throw new MissingTextSegmentDoesNotBelongToQuestionException();
        }
    }

    private void resetSingleChoiceAnswer(SingleChoiceAnswer answer) {
        answer.setSelectedOption(null);
    }

    private void resetMultipleChoiceAnswer(MultipleChoiceAnswer answer) {
        answer.getSelectedOptions().clear();
    }

    private void resetFillTheGapsAnswer(FillTheGapsAnswer answer) {
        answer.getGapAnswerSegments().clear();
    }

    private void resetTextLongAnswer(TextLongAnswer answer) {
        answer.setText(null);
    }
}
