package com.portfolio.lmsbackend.model.content.quiz;

import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "quiz")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private QuizGroup group;

    @OneToMany(mappedBy = "quiz", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private List<QuizQuestion> quizQuestions = new ArrayList<>();

    @Column(name = "shuffle_questions", nullable = false, updatable = false)
    private Boolean shuffleQuestions;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private Staff createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "quiz", cascade = ALL, orphanRemoval = true)
    private final Set<Attempt> attempts = new HashSet<>();

    public Quiz(QuizGroup group, List<QuizQuestion> quizQuestions,
                Boolean shuffleQuestions, Staff createdBy) {
        this.group = group;

        quizQuestions.forEach(q -> q.setQuiz(this));
        this.quizQuestions = quizQuestions;

        this.shuffleQuestions = shuffleQuestions;
        this.createdBy = createdBy;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Quiz quiz = (Quiz) o;
        return getId() != null && Objects.equals(getId(), quiz.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "group = " + group + ", " +
                "shuffleQuestions = " + shuffleQuestions + ", " +
                "createdBy = " + createdBy + ", " +
                "createdAt = " + createdAt + ")";
    }
}
