package com.portfolio.lmsbackend.model.course;

import com.portfolio.lmsbackend.enums.course.CourseStatus;
import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.course.coursestudent.CourseStudent;
import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.portfolio.lmsbackend.enums.course.CourseStatus.HIDDEN;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Course extends BaseEntity {
    @Column(name = "title", nullable = false, unique = true, length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "course", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private List<Section> sections = new ArrayList<>();

    @Enumerated(value = STRING)
    @Column(name = "status", nullable = false)
    private CourseStatus status = HIDDEN;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private Staff createdBy;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "course", cascade = ALL, orphanRemoval = true)
    private final Set<CourseStudent> courseStudents = new HashSet<>();

    public Course(String title, Category category, Staff createdBy) {
        this.title = title;
        this.category = category;
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "title = " + getTitle() + ", " +
                "category = " + getCategory() + ", " +
                "status = " + getStatus() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
