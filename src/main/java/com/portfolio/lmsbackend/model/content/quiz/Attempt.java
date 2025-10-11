package com.portfolio.lmsbackend.model.content.quiz;

import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.content.quiz.answer.*;
import com.portfolio.lmsbackend.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus.STARTED;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "attempt")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Attempt extends BaseEntity {
    @Enumerated(value = STRING)
    @Column(name = "status", nullable = false)
    private AttemptStatus status = STARTED;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false, updatable = false)
    private Quiz quiz;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "attempt", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private List<Answer> answers = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @Column(name = "started_at", nullable = false, updatable = false)
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    public Attempt(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
        this.answers = createAnswers(quiz);
    }

    private List<Answer> createAnswers(Quiz quiz) {
        List<Answer> answers = quiz.getQuizQuestions().stream()
                .map(this::createAnswer)
                .collect(Collectors.toList());

        if (Boolean.TRUE.equals(quiz.getShuffleQuestions())) {
            Collections.shuffle(answers);
        }

        return answers;
    }

    private Answer createAnswer(QuizQuestion quizQuestion) {
        return switch (quizQuestion.getQuestion().getType()) {
            case SINGLE_CHOICE -> new SingleChoiceAnswer(this, quizQuestion);
            case MULTIPLE_CHOICE -> new MultipleChoiceAnswer(this, quizQuestion);
            case FILL_THE_GAPS -> new FillTheGapsAnswer(this, quizQuestion);
            case TEXT_LONG -> new TextLongAnswer(this, quizQuestion);
        };
    }

    @AssertTrue
    protected boolean isFinishedAtValid() {
        if (finishedAt == null) {
            return true;
        }

        return finishedAt.isAfter(startedAt);
    }

    @AssertTrue
    protected boolean areAnswersBelongToQuiz() {
        return answers.stream()
                .allMatch(answer -> answer.getQuizQuestion().getQuiz().equals(quiz));
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "status = " + getStatus() + ", " +
                "quiz = " + getQuiz() + ", " +
                "user = " + getUser() + ", " +
                "startedAt = " + getStartedAt() + ", " +
                "finishedAt = " + getFinishedAt() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
