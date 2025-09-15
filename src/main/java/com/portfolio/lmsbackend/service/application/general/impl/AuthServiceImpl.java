package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.auth.request.*;
import com.portfolio.lmsbackend.exception.token.InvalidInvitationTokenException;
import com.portfolio.lmsbackend.exception.token.InvalidResetPasswordTokenException;
import com.portfolio.lmsbackend.exception.token.InvalidVerificationTokenException;
import com.portfolio.lmsbackend.exception.user.EmailAlreadyUsedException;
import com.portfolio.lmsbackend.model.invitation.StaffInvitation;
import com.portfolio.lmsbackend.model.token.InvitationToken;
import com.portfolio.lmsbackend.model.token.ResetPasswordToken;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.invitation.StaffInvitationRepository;
import com.portfolio.lmsbackend.repository.token.InvitationTokenRepository;
import com.portfolio.lmsbackend.repository.token.RefreshTokenRepository;
import com.portfolio.lmsbackend.repository.token.ResetPasswordTokenRepository;
import com.portfolio.lmsbackend.repository.token.VerificationTokenRepository;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import com.portfolio.lmsbackend.security.JwtMethods;
import com.portfolio.lmsbackend.security.JwtPair;
import com.portfolio.lmsbackend.service.application.general.AuthService;
import com.portfolio.lmsbackend.service.application.general.AuthSmtpMailSender;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final StaffInvitationRepository staffInvitationRepository;
    private final InvitationTokenRepository invitationTokenRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtMethods jwtMethods;
    private final AuthSmtpMailSender smtpMailSender;
    private final UserServiceHelper userServiceHelper;

    @Override
    public void registerStaff(StaffRegisterRequest registerRequest) {
        StaffInvitation staffInvitation = invitationTokenRepository.findByToken(registerRequest.invitationToken())
                .map(InvitationToken::getUserInvitation)
                .filter(StaffInvitation.class::isInstance)
                .map(StaffInvitation.class::cast)
                .orElseThrow(InvalidInvitationTokenException::new);

        staffInvitationRepository.deleteByEmail(staffInvitation.getEmail());

        if (userRepository.existsByEmail(staffInvitation.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        Staff staff = new Staff(
                staffInvitation.getFirstName(),
                staffInvitation.getLastName(),
                staffInvitation.getEmail(),
                passwordEncoder.encode(registerRequest.password()),
                staffInvitation.getRole()
        );

        userRepository.save(staff);
    }

    @Override
    public void registerStudent(StudentRegisterRequest registerRequest, String header) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new EmailAlreadyUsedException();
        }

        Student student = new Student(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password())
        );

        userServiceHelper.sendVerificationToken(student, header);
    }

    @Override
    @Transactional
    public void sendNewVerificationToken(NewVerificationTokenRequest newVerificationTokenRequest, String header) {
        userServiceHelper.sendVerificationToken(
                userServiceHelper.findByEmailOrThrow(newVerificationTokenRequest.email()),
                header
        );
    }

    @Override
    @Transactional
    public JwtPair verify(VerifyRequest verifyRequest) {
        User user = verificationTokenRepository.findByToken(verifyRequest.verificationToken())
                .orElseThrow(InvalidVerificationTokenException::new).getUser();

        user.setEmailVerified(true);
        user.getVerificationTokens().clear();
        userRepository.save(user);
        return jwtMethods.genJwtPair(user);
    }

    @Override
    public JwtPair login(LoginRequest loginRequest) {
        return jwtMethods.genJwtPair(
                (User) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )).getPrincipal()
        );
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Override
    @Transactional
    public void sendResetPasswordToken(ForgotPasswordRequest forgotPasswordRequest, String header) {
        User user = userServiceHelper.findByEmailOrThrow(forgotPasswordRequest.email());

        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user);
        user.getResetPasswordTokens().add(resetPasswordToken);
        user = userRepository.save(user);
        smtpMailSender.sendResetPasswordMessage(user.getEmail(), resetPasswordToken.getToken(), header);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = resetPasswordTokenRepository.findByToken(resetPasswordRequest.resetPasswordToken())
                .orElseThrow(InvalidResetPasswordTokenException::new).getUser();

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        user.getResetPasswordTokens().clear();
        userRepository.save(user);
    }

    @Override
    public JwtPair refresh(String refreshToken) {
        return jwtMethods.refresh(refreshToken);
    }
}
