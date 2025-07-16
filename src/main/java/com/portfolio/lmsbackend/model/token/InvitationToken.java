package com.portfolio.lmsbackend.model.token;

import com.portfolio.lmsbackend.model.invitation.UserInvitation;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invitation_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InvitationToken extends BaseTokenEntity {
    @ManyToOne
    @JoinColumn(name = "user_invitation_id", nullable = false, updatable = false)
    private UserInvitation userInvitation;

    public InvitationToken(UserInvitation userInvitation) {
        this.userInvitation = userInvitation;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "token = " + getToken() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "userInvitation = " + getUserInvitation() + ")";
    }
}
