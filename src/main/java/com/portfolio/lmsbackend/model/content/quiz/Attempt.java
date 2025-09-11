package com.portfolio.lmsbackend.model.content.quiz;

import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import com.portfolio.lmsbackend.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
