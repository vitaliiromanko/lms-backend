package com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction;

import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.user.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "quiz_group_access_restriction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("type")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class QuizGroupAccessRestriction {
    @EmbeddedId
    private QuizGroupAccessRestrictionId id;

    @MapsId("groupId")
    @ManyToOne
    private QuizGroup group;

    @MapsId("studentId")
    @ManyToOne
    private Student student;

    @Enumerated(value = STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private QuizGroupAccessRestrictionType type;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected QuizGroupAccessRestriction(QuizGroup group, Student student, QuizGroupAccessRestrictionType type) {
        this.id = new QuizGroupAccessRestrictionId(group.getId(), student.getId());
        this.group = group;
        this.student = student;
        this.type = type;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        QuizGroupAccessRestriction that = (QuizGroupAccessRestriction) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
