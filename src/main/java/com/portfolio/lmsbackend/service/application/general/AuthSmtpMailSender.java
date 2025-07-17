package com.portfolio.lmsbackend.service.application.general;

public interface AuthSmtpMailSender {
    void sendStaffInvitationMessage(String emailTo, String inviteToken, String baseUrl);

    void sendVerificationMessage(String emailTo, String verificationToken, String baseUrl);

    void sendResetPasswordMessage(String emailTo, String resetPasswordToken, String baseUrl);
}
