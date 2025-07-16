package com.portfolio.lmsbackend.model.user;

import com.portfolio.lmsbackend.enums.user.StaffRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "staff")
@DiscriminatorValue("STAFF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Staff extends User {
    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Enumerated(value = STRING)
    @Column(name = "role", nullable = false)
    private StaffRole role;

    public Staff(String firstName, String lastName, String email, String password,
                 String phoneNumber, StaffRole role) {
        super(firstName, lastName, email, password, true);
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    protected String getRoleString() {
        return role.name();
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
                "phoneNumber = " + getPhoneNumber() + ", " +
                "role = " + getRole() + ", " +
                "status = " + getStatus() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
