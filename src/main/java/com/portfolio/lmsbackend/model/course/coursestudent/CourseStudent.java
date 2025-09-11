package com.portfolio.lmsbackend.model.course.coursestudent;

import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.model.user.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "course_student")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CourseStudent {
    @EmbeddedId
    private CourseStudentId id;

    @MapsId("courseId")
    @ManyToOne
    private Course course;

    @MapsId("studentId")
    @ManyToOne
    private Student student;

    @CreatedDate
    @Column(name = "enrolled_at", nullable = false, updatable = false)
    private LocalDateTime enrolledAt;

    public CourseStudent(Course course, Student student) {
        this.id = new CourseStudentId(course.getId(), student.getId());
        this.course = course;
        this.student = student;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CourseStudent that = (CourseStudent) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "course = " + course + ", " +
                "student = " + student + ", " +
                "enrolledAt = " + enrolledAt + ")";
    }
}
