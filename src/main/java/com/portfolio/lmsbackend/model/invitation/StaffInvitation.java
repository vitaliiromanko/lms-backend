package com.portfolio.lmsbackend.model.invitation;

import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "staff_invitation")
@DiscriminatorValue("STAFF_INVITATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class StaffInvitation extends UserInvitation {
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(value = STRING)
    @Column(name = "role", nullable = false)
    private StaffRole role;

    public StaffInvitation(String firstName, String lastName, String email,
                           StaffRole role, Staff invitedBy) {
        super(invitedBy);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "firstName = " + getFirstName() + ", " +
                "lastName = " + getLastName() + ", " +
                "email = " + getEmail() + ", " +
                "role = " + getRole() + ", " +
                "invitedBy = " + getInvitedBy() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
