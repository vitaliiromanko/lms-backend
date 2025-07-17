package com.portfolio.lmsbackend.service.application.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.invitation.request.StaffInvitationRequest;

public interface InvitationService {
    void inviteStaff(String userId, StaffInvitationRequest invitationRequest, String header);
}
