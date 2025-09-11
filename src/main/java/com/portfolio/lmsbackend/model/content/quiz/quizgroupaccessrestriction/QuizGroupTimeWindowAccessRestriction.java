package com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction;

import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.user.Student;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType.TIME_WINDOW;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TIME_WINDOW")
@Getter
public class QuizGroupTimeWindowAccessRestriction extends QuizGroupAccessRestriction {
    @NotNull
    @Column(name = "available_from", updatable = false)
    private LocalDateTime availableFrom;

    @NotNull
    @Column(name = "available_until", updatable = false)
    private LocalDateTime availableUntil;

    public QuizGroupTimeWindowAccessRestriction(QuizGroup group, Student student,
                                                LocalDateTime availableFrom, LocalDateTime availableUntil) {
        super(group, student, TIME_WINDOW);
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
    }

    @AssertTrue
    public boolean isValidTimeWindow() {
        return availableUntil.isAfter(availableFrom)
                && availableUntil.isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "group = " + getGroup() + ", " +
                "student = " + getStudent() + ", " +
                "type = " + getType() + ", " +
                "availableFrom = " + getAvailableFrom() + ", " +
                "availableUntil = " + getAvailableUntil() + ", " +
                "createdAt = " + getCreatedAt() + ")";
    }
}
