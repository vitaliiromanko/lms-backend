package com.portfolio.lmsbackend.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@DiscriminatorValue("STUDENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Student extends User {
    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, false);
    }

    @Override
    protected String getRoleString() {
        return "STUDENT";
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
