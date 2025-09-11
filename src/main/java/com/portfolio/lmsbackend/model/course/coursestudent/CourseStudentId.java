package com.portfolio.lmsbackend.model.course.coursestudent;

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
public class CourseStudentId implements Serializable {
    @Column(name = "course_id", nullable = false, updatable = false)
    private UUID courseId;

    @Column(name = "student_id", nullable = false, updatable = false)
    private UUID studentId;

    public CourseStudentId(UUID courseId, UUID studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CourseStudentId that = (CourseStudentId) o;
        return getCourseId() != null && Objects.equals(getCourseId(), that.getCourseId())
                && getStudentId() != null && Objects.equals(getStudentId(), that.getStudentId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(courseId, studentId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "courseId = " + courseId + ", " +
                "studentId = " + studentId + ")";
    }
}
