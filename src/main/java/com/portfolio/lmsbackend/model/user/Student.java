package com.portfolio.lmsbackend.model.user;

import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.model.course.coursestudent.CourseStudent;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "student")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Student extends User {
    @OneToMany(mappedBy = "student", cascade = ALL, orphanRemoval = true)
    private final Set<CourseStudent> courseStudents = new HashSet<>();

    public Student(String firstName, String lastName, String email, String password) {
        super(UserType.STUDENT, firstName, lastName, email, password, false);
    }

    @Override
    protected String getRoleString() {
        return getType().name();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "firstName = " + getFirstName() + ", " +
                "lastName = " + getLastName() + ", " +
                "email = " + getEmail() + ", " +
                "emailVerified = " + isEmailVerified() + ", " +
                "photo = " + getPhoto() + ", " +
                "status = " + getStatus() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
