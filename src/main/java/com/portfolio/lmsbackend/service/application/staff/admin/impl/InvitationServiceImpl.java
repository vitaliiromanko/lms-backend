package com.portfolio.lmsbackend.service.application.staff.admin.impl;

import com.portfolio.lmsbackend.dto.staff.admin.invitation.request.StaffInvitationRequest;
import com.portfolio.lmsbackend.exception.user.EmailAlreadyUsedException;
import com.portfolio.lmsbackend.model.invitation.StaffInvitation;
import com.portfolio.lmsbackend.model.token.InvitationToken;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.repository.invitation.StaffInvitationRepository;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import com.portfolio.lmsbackend.service.application.general.AuthSmtpMailSender;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.admin.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final StaffInvitationRepository staffInvitationRepository;
    private final UserRepository userRepository;
    private final AuthSmtpMailSender smtpMailSender;
    private final UserServiceHelper userServiceHelper;

    @Override
    public void inviteStaff(UUID userId, StaffInvitationRequest invitationRequest, String header) {
        if (userRepository.existsByEmail(invitationRequest.email()) ||
                staffInvitationRepository.existsByEmail(invitationRequest.email())) {
            throw new EmailAlreadyUsedException();
        }

        StaffInvitation staffInvitation = new StaffInvitation(
                invitationRequest.firstName(),
                invitationRequest.lastName(),
                invitationRequest.email(),
                invitationRequest.role(),
                userServiceHelper.findByIdAndTypeOrThrow(userId, Staff.class)
        );

        InvitationToken invitationToken = new InvitationToken(staffInvitation);
        staffInvitation.getInvitationTokens().add(invitationToken);
        staffInvitation = staffInvitationRepository.save(staffInvitation);
        smtpMailSender.sendStaffInvitationMessage(staffInvitation.getEmail(),
                invitationToken.getToken(), header);
    }
}
