package com.portfolio.lmsbackend.model.invitation;

import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.token.InvitationToken;
import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "user_invitation")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_invitation_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class UserInvitation extends BaseEntity {
    @OneToMany(targetEntity = InvitationToken.class,
            mappedBy = "userInvitation", cascade = ALL, orphanRemoval = true)
    private Set<InvitationToken> invitationTokens = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "invited_by", nullable = false, updatable = false)
    private Staff invitedBy;

    protected UserInvitation(Staff invitedBy) {
        this.invitedBy = invitedBy;
    }
}
