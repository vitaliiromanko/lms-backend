package com.portfolio.lmsbackend.repository.invitation;

import com.portfolio.lmsbackend.model.invitation.UserInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserInvitationRepository extends JpaRepository<UserInvitation, UUID> {
}
