package com.portfolio.lmsbackend.model.content.quiz.question;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "choice_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChoiceOption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Lob
    @Column(name = "text", nullable = false, updatable = false)
    private String text;

    @Column(name = "is_correct", nullable = false, updatable = false)
    private Boolean correct;

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne
    @JoinColumn(name = "choice_question_id", nullable = false, updatable = false)
    private ChoiceQuestion choiceQuestion;

    public ChoiceOption(String text, Boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ChoiceOption that = (ChoiceOption) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "text = " + text + ", " +
                "correct = " + correct + ", " +
                "choiceQuestion = " + choiceQuestion + ")";
    }
}
