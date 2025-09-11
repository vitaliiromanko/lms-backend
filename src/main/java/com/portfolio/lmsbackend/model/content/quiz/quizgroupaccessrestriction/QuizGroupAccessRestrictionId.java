package com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuizGroupAccessRestrictionId implements Serializable {
    @Column(name = "group_id", nullable = false, updatable = false)
    private UUID groupId;

    @Column(name = "student_id", nullable = false, updatable = false)
    private UUID studentId;

    protected QuizGroupAccessRestrictionId(UUID groupId, UUID studentId) {
        this.groupId = groupId;
        this.studentId = studentId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        QuizGroupAccessRestrictionId that = (QuizGroupAccessRestrictionId) o;
        return getGroupId() != null && Objects.equals(getGroupId(), that.getGroupId())
                && getStudentId() != null && Objects.equals(getStudentId(), that.getStudentId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(groupId, studentId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "groupId = " + groupId + ", " +
                "studentId = " + studentId + ")";
    }
}
