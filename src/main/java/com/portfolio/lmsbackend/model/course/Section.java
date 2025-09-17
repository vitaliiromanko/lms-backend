package com.portfolio.lmsbackend.model.course;

import com.portfolio.lmsbackend.enums.course.SectionStatus;
import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.content.SectionContent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.portfolio.lmsbackend.enums.course.SectionStatus.HIDDEN;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "section")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Section extends BaseEntity {
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false, updatable = false)
    private Course course;

    @Enumerated(value = STRING)
    @Column(name = "status", nullable = false)
    private SectionStatus status = HIDDEN;

    @OneToMany(mappedBy = "section", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private List<SectionContent> contents = new ArrayList<>();

    public Section(String title, Course course) {
        this.title = title;
        this.course = course;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "title = " + getTitle() + ", " +
                "course = " + getCourse() + ", " +
                "status = " + getStatus() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
