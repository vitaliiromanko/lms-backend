package com.portfolio.lmsbackend.service.application.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.invitation.request.StaffInvitationRequest;

import java.util.UUID;

public interface InvitationService {
    void inviteStaff(UUID userId, StaffInvitationRequest invitationRequest, String header);
}
