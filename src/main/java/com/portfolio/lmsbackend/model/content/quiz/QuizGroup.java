package com.portfolio.lmsbackend.model.content.quiz;

import com.portfolio.lmsbackend.model.content.SectionContent;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;
import com.portfolio.lmsbackend.model.course.Section;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.portfolio.lmsbackend.enums.content.SectionContentType.QUIZ_GROUP;
import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "quiz_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class QuizGroup extends SectionContent {
    @OneToMany(mappedBy = "group", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private List<Quiz> quizzes = new ArrayList<>();

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "max_attempts")
    @Min(value = 1)
    private Integer maxAttempts;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "group", cascade = ALL, orphanRemoval = true)
    private Set<QuizGroupAccessRestriction> accessRestrictions = new HashSet<>();

    public QuizGroup(String title, Section section, Duration duration, Integer maxAttempts) {
        super(QUIZ_GROUP, title, section);
        this.duration = duration;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "type = " + getType() + ", " +
                "title = " + getTitle() + ", " +
                "status = " + getStatus() + ", " +
                "duration = " + getDuration() + ", " +
                "maxAttempts = " + getMaxAttempts() + ", " +
                "section = " + getSection() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
