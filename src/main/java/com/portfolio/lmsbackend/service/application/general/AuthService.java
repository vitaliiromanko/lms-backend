package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.auth.request.*;
import com.portfolio.lmsbackend.security.JwtPair;

public interface AuthService {
    void registerStaff(StaffRegisterRequest registerRequest);

    void registerStudent(StudentRegisterRequest registerRequest, String header);

    void sendNewVerificationToken(NewVerificationTokenRequest newVerificationTokenRequest, String header);

    JwtPair verify(VerifyRequest verifyRequest);

    JwtPair login(LoginRequest loginRequest);

    void logout(String refreshToken);

    void sendResetPasswordToken(ForgotPasswordRequest forgotPasswordRequest, String header);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    JwtPair refresh(String refreshToken);
}
