package com.portfolio.lmsbackend.repository.invitation;

import com.portfolio.lmsbackend.model.invitation.StaffInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface StaffInvitationRepository extends JpaRepository<StaffInvitation, UUID> {
    boolean existsByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
