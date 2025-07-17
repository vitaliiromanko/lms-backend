package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.service.application.general.AuthSmtpMailSender;
import com.portfolio.lmsbackend.service.infrastructure.email.SmtpMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthSmtpMailSenderImpl implements AuthSmtpMailSender {
    private final SmtpMailSender smtpMailSender;

    @Override
    public void sendStaffInvitationMessage(String emailTo, String inviteToken, String baseUrl) {
        smtpMailSender.sendMessage(
                emailTo,
                "Staff invitation",
                String.format("Follow the link to complete registration:\n%s/staff/register/%s",
                        baseUrl, inviteToken)
        );
    }

    @Override
    public void sendVerificationMessage(String emailTo, String verificationToken, String baseUrl) {
        smtpMailSender.sendMessage(
                emailTo,
                "Email verification",
                String.format("Follow the link to verify your email:\n%s/verify/%s",
                        baseUrl, verificationToken)
        );
    }

    @Override
    public void sendResetPasswordMessage(String emailTo, String resetPasswordToken, String baseUrl) {
        smtpMailSender.sendMessage(
                emailTo,
                "Reset password",
                String.format("Follow the link to reset your account password:\n%s/reset/%s",
                        baseUrl, resetPasswordToken)
        );
    }
}
