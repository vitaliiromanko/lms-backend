package com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction;

import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.user.Student;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType.BLOCKED;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("BLOCKED")
public class QuizGroupBlockedAccessRestriction extends QuizGroupAccessRestriction {
    public QuizGroupBlockedAccessRestriction(QuizGroup group, Student student) {
        super(group, student, BLOCKED);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "group = " + getGroup() + ", " +
                "student = " + getStudent() + ", " +
                "type = " + getType() + ", " +
                "createdAt = " + getCreatedAt() + ")";
    }
}
