package com.portfolio.lmsbackend.model.content.quiz.answer;

import com.portfolio.lmsbackend.enums.content.quiz.AnswerStatus;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.quiz.AnswerStatus.PENDING_GRADING;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "answer")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class Answer {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "attempt_id", nullable = false, updatable = false)
    private Attempt attempt;

    @Enumerated(value = STRING)
    @Column(name = "status", nullable = false)
    private AnswerStatus status = PENDING_GRADING;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "quiz_question_id", nullable = false, updatable = false)
    private QuizQuestion quizQuestion;

    @Column(name = "score")
    private Double score;

    protected Answer(Attempt attempt, QuizQuestion quizQuestion) {
        this.attempt = attempt;
        this.quizQuestion = quizQuestion;
    }

    @AssertTrue
    protected boolean isSameQuizValid() {
        return attempt.getQuiz().equals(quizQuestion.getQuiz());
    }

    @AssertTrue
    protected boolean isQuestionTypeValid() {
        return quizQuestion.getQuestion().getType().equals(getSupportedQuestionType());
    }

    protected abstract QuestionType getSupportedQuestionType();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Answer answer = (Answer) o;
        return getId() != null && Objects.equals(getId(), answer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
