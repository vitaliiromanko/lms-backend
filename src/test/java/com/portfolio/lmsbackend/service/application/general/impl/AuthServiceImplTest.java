package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.auth.request.*;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.exception.token.InvalidInvitationTokenException;
import com.portfolio.lmsbackend.exception.token.InvalidResetPasswordTokenException;
import com.portfolio.lmsbackend.exception.token.InvalidVerificationTokenException;
import com.portfolio.lmsbackend.exception.user.EmailAlreadyUsedException;
import com.portfolio.lmsbackend.model.invitation.StaffInvitation;
import com.portfolio.lmsbackend.model.invitation.UserInvitation;
import com.portfolio.lmsbackend.model.token.InvitationToken;
import com.portfolio.lmsbackend.model.token.ResetPasswordToken;
import com.portfolio.lmsbackend.model.token.VerificationToken;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.invitation.StaffInvitationRepository;
import com.portfolio.lmsbackend.repository.token.InvitationTokenRepository;
import com.portfolio.lmsbackend.repository.token.RefreshTokenRepository;
import com.portfolio.lmsbackend.repository.token.ResetPasswordTokenRepository;
import com.portfolio.lmsbackend.repository.token.VerificationTokenRepository;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import com.portfolio.lmsbackend.security.general.JwtMethods;
import com.portfolio.lmsbackend.security.general.JwtPair;
import com.portfolio.lmsbackend.service.application.general.AuthSmtpMailSender;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl Unit Tests")
class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private StaffInvitationRepository staffInvitationRepository;
    @Mock
    private InvitationTokenRepository invitationTokenRepository;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtMethods jwtMethods;
    @Mock
    private AuthSmtpMailSender smtpMailSender;
    @Mock
    private UserServiceHelper userServiceHelper;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Nested
    @DisplayName("RegisterStaff Tests")
    class RegisterStaffTests {
        private StaffRegisterRequest registerRequest;
        private InvitationToken invitationToken;

        @BeforeEach
        void setUp() {
            this.registerRequest = new StaffRegisterRequest(
                    "invitation_token",
                    "password"
            );

            Staff invitedBy = new Staff(
                    "FirstName",
                    "LastName",
                    "user1@example.com",
                    "password",
                    StaffRole.ADMINISTRATOR
            );

            LocalDateTime userCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(invitedBy, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(invitedBy, "createdAt", userCreationTime);
            ReflectionTestUtils.setField(invitedBy, "updatedAt", userCreationTime);

            UserInvitation userInvitation = new StaffInvitation(
                    "FirstName",
                    "LastName",
                    "user2@example.com",
                    StaffRole.INSTRUCTOR,
                    invitedBy
            );

            LocalDateTime userInvitationCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(userInvitation, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(userInvitation, "createdAt", userInvitationCreationTime);
            ReflectionTestUtils.setField(userInvitation, "updatedAt", userInvitationCreationTime);

            this.invitationToken = new InvitationToken(userInvitation);
            userInvitation.getInvitationTokens().add(invitationToken);

            LocalDateTime invitationTokenCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(this.invitationToken, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(this.invitationToken, "createdAt", invitationTokenCreationTime);
        }

        @Test
        @DisplayName("Should register staff successfully when valid request")
        void shouldRegisterStaffSuccessfullyWhenValidRequest() {
            // Given
            StaffInvitation staffInvitation = (StaffInvitation) invitationToken.getUserInvitation();
            String encodedPassword = "encoded_password";

            when(invitationTokenRepository.findByToken(registerRequest.invitationToken()))
                    .thenReturn(Optional.of(invitationToken));

            when(userRepository.existsByEmail(staffInvitation.getEmail()))
                    .thenReturn(false);

            when(passwordEncoder.encode(registerRequest.password()))
                    .thenReturn(encodedPassword);

            // When
            authServiceImpl.registerStaff(registerRequest);

            // Then
            verify(invitationTokenRepository, times(1))
                    .findByToken(registerRequest.invitationToken());

            verify(staffInvitationRepository, times(1))
                    .deleteByEmail(staffInvitation.getEmail());

            verify(userRepository, times(1))
                    .existsByEmail(staffInvitation.getEmail());

            verify(passwordEncoder, times(1))
                    .encode(registerRequest.password());

            ArgumentCaptor<Staff> staffCaptor = ArgumentCaptor.forClass(Staff.class);
            verify(userRepository, times(1))
                    .save(staffCaptor.capture());

            Staff savedStaff = staffCaptor.getValue();
            assertAll(
                    () -> assertEquals(staffInvitation.getFirstName(), savedStaff.getFirstName()),
                    () -> assertEquals(staffInvitation.getLastName(), savedStaff.getLastName()),
                    () -> assertEquals(staffInvitation.getEmail(), savedStaff.getEmail()),
                    () -> assertEquals(encodedPassword, savedStaff.getPassword()),
                    () -> assertEquals(staffInvitation.getRole(), savedStaff.getRole())
            );
        }

        @Test
        @DisplayName("Should throw InvalidInvitationTokenException when invitation not found")
        void shouldThrowInvalidInvitationTokenExceptionWhenInvitationNotFound() {
            // Given
            when(invitationTokenRepository.findByToken(registerRequest.invitationToken()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(
                    InvalidInvitationTokenException.class,
                    () -> authServiceImpl.registerStaff(registerRequest)
            );

            verify(invitationTokenRepository, times(1))
                    .findByToken(registerRequest.invitationToken());

            verifyNoInteractions(staffInvitationRepository);

            verifyNoInteractions(userRepository);
        }

        @Test
        @DisplayName("Should throw InvalidInvitationTokenException when invitation type is invalid")
        void shouldThrowInvalidInvitationTokenExceptionWhenInvitationTypeIsInvalid() {
            // Given
            UserInvitation userInvitation = new UserInvitation(invitationToken.getUserInvitation().getInvitedBy()) {
            };
            ReflectionTestUtils.setField(invitationToken, "userInvitation", userInvitation);

            when(invitationTokenRepository.findByToken(registerRequest.invitationToken()))
                    .thenReturn(Optional.of(invitationToken));

            // When & Then
            assertThrows(
                    InvalidInvitationTokenException.class,
                    () -> authServiceImpl.registerStaff(registerRequest)
            );

            verify(invitationTokenRepository, times(1))
                    .findByToken(registerRequest.invitationToken());

            verifyNoInteractions(staffInvitationRepository);

            verifyNoInteractions(userRepository);
        }

        @Test
        @DisplayName("Should throw EmailAlreadyUsedException when email from staff invitation is already used")
        void shouldThrowEmailAlreadyUsedExceptionWhenEmailFromStaffInvitationIsAlreadyUsed() {
            // Given
            StaffInvitation staffInvitation = (StaffInvitation) invitationToken.getUserInvitation();

            when(invitationTokenRepository.findByToken(registerRequest.invitationToken()))
                    .thenReturn(Optional.of(invitationToken));

            when(userRepository.existsByEmail(staffInvitation.getEmail()))
                    .thenReturn(true);

            // When & Then
            assertThrows(
                    EmailAlreadyUsedException.class,
                    () -> authServiceImpl.registerStaff(registerRequest)
            );

            verify(invitationTokenRepository, times(1))
                    .findByToken(registerRequest.invitationToken());

            verify(staffInvitationRepository, times(1))
                    .deleteByEmail(staffInvitation.getEmail());

            verify(userRepository, times(1))
                    .existsByEmail(staffInvitation.getEmail());

            verifyNoMoreInteractions(userRepository);
        }
    }

    @Nested
    @DisplayName("RegisterStudent Tests")
    class RegisterStudentTests {
        private StudentRegisterRequest registerRequest;
        private String header;

        @BeforeEach
        void setUp() {
            this.registerRequest = new StudentRegisterRequest(
                    "FirstName",
                    "LastName",
                    "user3@example.com",
                    "password"
            );

            this.header = "header";
        }

        @Test
        @DisplayName("Should register student successfully when valid request")
        void shouldRegisterStudentSuccessfullyWhenValidRequest() {
            // Given
            String encodedPassword = "encoded_password";

            when(userRepository.existsByEmail(registerRequest.email()))
                    .thenReturn(false);

            when(passwordEncoder.encode(registerRequest.password()))
                    .thenReturn(encodedPassword);

            // When
            authServiceImpl.registerStudent(registerRequest, header);

            // Then
            verify(userRepository, times(1))
                    .existsByEmail(registerRequest.email());

            verify(passwordEncoder, times(1))
                    .encode(registerRequest.password());

            ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
            verify(userServiceHelper, times(1))
                    .sendVerificationToken(studentCaptor.capture(), eq(header));

            Student savedStudent = studentCaptor.getValue();
            assertAll(
                    () -> assertEquals(registerRequest.firstName(), savedStudent.getFirstName()),
                    () -> assertEquals(registerRequest.lastName(), savedStudent.getLastName()),
                    () -> assertEquals(registerRequest.email(), savedStudent.getEmail()),
                    () -> assertEquals(encodedPassword, savedStudent.getPassword())
            );
        }

        @Test
        @DisplayName("Should throw EmailAlreadyUsedException when email from request is already used")
        void shouldThrowEmailAlreadyUsedExceptionWhenEmailFromRequestIsAlreadyUsed() {
            // Given
            when(userRepository.existsByEmail(registerRequest.email()))
                    .thenReturn(true);

            // When & Then
            assertThrows(
                    EmailAlreadyUsedException.class,
                    () -> authServiceImpl.registerStudent(registerRequest, header)
            );

            verify(userRepository, times(1))
                    .existsByEmail(registerRequest.email());

            verifyNoMoreInteractions(userRepository);

            verifyNoInteractions(userServiceHelper);
        }
    }

    @Nested
    @DisplayName("SendNewVerificationToken Tests")
    class SendNewVerificationTokenTests {
        private NewVerificationTokenRequest newVerificationTokenRequest;
        private String header;
        private User user;

        @BeforeEach
        void setUp() {
            String email = "user4@example.com";

            this.newVerificationTokenRequest = new NewVerificationTokenRequest(
                    email
            );

            this.header = "header";

            this.user = new User() {
                @Override
                protected String getRoleString() {
                    return "TEST";
                }
            };

            this.user.setFirstName("FirstName");
            this.user.setLastName("LastName");
            this.user.setEmail(email);
            this.user.setPassword("encoded_password");
            this.user.setEmailVerified(true);

            LocalDateTime userCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(this.user, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(this.user, "createdAt", userCreationTime);
            ReflectionTestUtils.setField(this.user, "updatedAt", userCreationTime);
        }

        @Test
        @DisplayName("Should send new verification token successfully when valid request")
        void shouldSendNewVerificationTokenSuccessfullyWhenValidRequest() {
            // Given
            when(userServiceHelper.findByEmailOrThrow(newVerificationTokenRequest.email()))
                    .thenReturn(user);

            // When
            authServiceImpl.sendNewVerificationToken(newVerificationTokenRequest, header);

            // Then
            verify(userServiceHelper, times(1))
                    .sendVerificationToken(user, header);

            verify(userServiceHelper, times(1))
                    .findByEmailOrThrow(newVerificationTokenRequest.email());
        }
    }

    @Nested
    @DisplayName("Verify Tests")
    class VerifyTests {
        private VerifyRequest verifyRequest;
        private VerificationToken verificationToken;
        private JwtPair jwtPair;

        @BeforeEach
        void setUp() {
            this.verifyRequest = new VerifyRequest(
                    "verification_token"
            );

            User user = new User() {
                @Override
                protected String getRoleString() {
                    return "TEST";
                }
            };

            user.setFirstName("FirstName");
            user.setLastName("LastName");
            user.setEmail("user5@example.com");
            user.setPassword("encoded_password");
            user.setEmailVerified(false);

            LocalDateTime userCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(user, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(user, "createdAt", userCreationTime);
            ReflectionTestUtils.setField(user, "updatedAt", userCreationTime);

            this.verificationToken = new VerificationToken(user);

            LocalDateTime verificationTokenCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(this.verificationToken, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(this.verificationToken, "createdAt", verificationTokenCreationTime);

            user.getVerificationTokens().add(this.verificationToken);

            this.jwtPair = new JwtPair(
                    "access_token",
                    "refresh_token"
            );
        }

        @Test
        @DisplayName("Should verify email successfully when valid request")
        void shouldVerifyEmailSuccessfullyWhenValidRequest() {
            // Given
            when(verificationTokenRepository.findByToken(verifyRequest.verificationToken()))
                    .thenReturn(Optional.of(verificationToken));

            when(jwtMethods.genJwtPair(verificationToken.getUser()))
                    .thenReturn(jwtPair);

            // When
            JwtPair genJwtPair = authServiceImpl.verify(verifyRequest);

            // Then
            verify(verificationTokenRepository, times(1))
                    .findByToken(verifyRequest.verificationToken());

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, times(1))
                    .save(userCaptor.capture());

            verify(jwtMethods, times(1))
                    .genJwtPair(verificationToken.getUser());

            User savedUser = userCaptor.getValue();
            assertAll(
                    () -> assertEquals(verificationToken.getUser(), savedUser),
                    () -> assertTrue(savedUser.isEmailVerified()),
                    () -> assertTrue(savedUser.getVerificationTokens().isEmpty())
            );

            assertEquals(jwtPair, genJwtPair);
        }

        @Test
        @DisplayName("Should throw InvalidVerificationTokenException when verification token not found")
        void shouldThrowInvalidVerificationTokenExceptionWhenVerificationTokenNotFound() {
            // Given
            when(verificationTokenRepository.findByToken(verifyRequest.verificationToken()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(
                    InvalidVerificationTokenException.class,
                    () -> authServiceImpl.verify(verifyRequest)
            );

            verify(verificationTokenRepository, times(1))
                    .findByToken(verifyRequest.verificationToken());

            verifyNoInteractions(userRepository);

            verifyNoInteractions(jwtMethods);
        }
    }

    @Nested
    @DisplayName("Login Tests")
    class LoginTests {
        private LoginRequest loginRequest;
        private JwtPair jwtPair;
        private User user;

        @BeforeEach
        void setUp() {
            String email = "user6@example.com";
            String password = "encoded_password";

            this.loginRequest = new LoginRequest(
                    email,
                    password
            );

            this.jwtPair = new JwtPair(
                    "access_token",
                    "refresh_token"
            );

            this.user = new User() {
                @Override
                protected String getRoleString() {
                    return "TEST";
                }
            };

            this.user.setFirstName("FirstName");
            this.user.setLastName("LastName");
            this.user.setEmail(email);
            this.user.setPassword(password);
            this.user.setEmailVerified(true);

            LocalDateTime userCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(this.user, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(this.user, "createdAt", userCreationTime);
            ReflectionTestUtils.setField(this.user, "updatedAt", userCreationTime);
        }

        @Test
        @DisplayName("Should login user successfully when valid request")
        void shouldLoginUserSuccessfullyWhenValidRequest() {
            // Given
            Authentication authMock = mock(Authentication.class);

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authMock);

            when(authMock.getPrincipal())
                    .thenReturn(user);

            when(jwtMethods.genJwtPair(user))
                    .thenReturn(jwtPair);

            // When
            JwtPair genJwtPair = authServiceImpl.login(loginRequest);

            // Then
            ArgumentCaptor<Authentication> authCaptor = ArgumentCaptor.forClass(Authentication.class);
            verify(authenticationManager, times(1))
                    .authenticate(authCaptor.capture());

            verify(jwtMethods, times(1))
                    .genJwtPair(user);

            UsernamePasswordAuthenticationToken capturedToken = (UsernamePasswordAuthenticationToken) authCaptor.getValue();
            assertAll(
                    () -> assertEquals(loginRequest.email(), capturedToken.getPrincipal()),
                    () -> assertEquals(loginRequest.password(), capturedToken.getCredentials())
            );

            assertEquals(jwtPair, genJwtPair);
        }
    }

    @Nested
    @DisplayName("Logout Tests")
    class LogoutTests {
        private String refreshToken;

        @BeforeEach
        void setUp() {
            this.refreshToken = "refresh_token";
        }

        @Test
        @DisplayName("Should logout user successfully when valid request")
        void shouldLogoutUserSuccessfullyWhenValidRequest() {
            // When
            authServiceImpl.logout(refreshToken);

            // Then
            verify(refreshTokenRepository, times(1))
                    .deleteByToken(refreshToken);
        }
    }

    @Nested
    @DisplayName("SendResetPasswordToken Tests")
    class SendResetPasswordTokenTests {
        private ForgotPasswordRequest forgotPasswordRequest;
        private String header;
        private User user;

        @BeforeEach
        void setUp() {
            String email = "user7@example.com";

            this.forgotPasswordRequest = new ForgotPasswordRequest(
                    email
            );

            this.header = "header";

            this.user = new User() {
                @Override
                protected String getRoleString() {
                    return "TEST";
                }
            };

            this.user.setFirstName("FirstName");
            this.user.setLastName("LastName");
            this.user.setEmail(email);
            this.user.setPassword("encoded_password");
            this.user.setEmailVerified(true);

            LocalDateTime userCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(this.user, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(this.user, "createdAt", userCreationTime);
            ReflectionTestUtils.setField(this.user, "updatedAt", userCreationTime);
        }

        @Test
        @DisplayName("Should send reset password token successfully when valid request")
        void shouldSendResetPasswordTokenSuccessfullyWhenValidRequest() {
            // Given
            when(userServiceHelper.findByEmailOrThrow(forgotPasswordRequest.email()))
                    .thenReturn(user);

            when(userRepository.save(user))
                    .thenReturn(user);

            // When
            authServiceImpl.sendResetPasswordToken(forgotPasswordRequest, header);

            // Then
            verify(userServiceHelper, times(1))
                    .findByEmailOrThrow(forgotPasswordRequest.email());

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, times(1))
                    .save(userCaptor.capture());

            ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);
            verify(smtpMailSender, times(1))
                    .sendResetPasswordMessage(emailCaptor.capture(), tokenCaptor.capture(), eq(header));

            User savedUser = userCaptor.getValue();
            String emailTo = savedUser.getEmail();
            String sentToken = tokenCaptor.getValue();

            assertAll(
                    () -> assertEquals(user, savedUser),
                    () -> assertFalse(savedUser.getResetPasswordTokens().isEmpty())
            );

            assertAll(
                    () -> assertEquals(savedUser.getEmail(), emailTo),
                    () -> assertEquals(savedUser.getResetPasswordTokens().iterator().next().getToken(), sentToken)
            );
        }
    }

    @Nested
    @DisplayName("ResetPassword Tests")
    class ResetPasswordTests {
        private ResetPasswordRequest resetPasswordRequest;
        private ResetPasswordToken resetPasswordToken;

        @BeforeEach
        void setUp() {
            String token = "token";

            this.resetPasswordRequest = new ResetPasswordRequest(
                    token,
                    "new_password"
            );

            User user = new User() {
                @Override
                protected String getRoleString() {
                    return "TEST";
                }
            };

            user.setFirstName("FirstName");
            user.setLastName("LastName");
            user.setEmail("user8@example.com");
            user.setPassword("encoded_password");
            user.setEmailVerified(false);

            LocalDateTime userCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(user, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(user, "createdAt", userCreationTime);
            ReflectionTestUtils.setField(user, "updatedAt", userCreationTime);

            this.resetPasswordToken = new ResetPasswordToken(user);

            LocalDateTime resetPasswordTokenCreationTime = LocalDateTime.now().minusMinutes(10);
            ReflectionTestUtils.setField(this.resetPasswordToken, "id", UUID.randomUUID());
            ReflectionTestUtils.setField(this.resetPasswordToken, "token", token);
            ReflectionTestUtils.setField(this.resetPasswordToken, "createdAt", resetPasswordTokenCreationTime);

            user.getResetPasswordTokens().add(this.resetPasswordToken);
        }

        @Test
        @DisplayName("Should reset user password successfully when valid request")
        void shouldResetUserPasswordSuccessfullyWhenValidRequest() {
            // Given
            String encodedNewPassword = "encoded_new_password";

            when(resetPasswordTokenRepository.findByToken(resetPasswordRequest.resetPasswordToken()))
                    .thenReturn(Optional.of(resetPasswordToken));

            when(passwordEncoder.encode(resetPasswordRequest.newPassword()))
                    .thenReturn(encodedNewPassword);

            // When
            authServiceImpl.resetPassword(resetPasswordRequest);

            // Then
            verify(resetPasswordTokenRepository, times(1))
                    .findByToken(resetPasswordRequest.resetPasswordToken());

            verify(passwordEncoder, times(1))
                    .encode(resetPasswordRequest.newPassword());

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, times(1))
                    .save(userCaptor.capture());

            User savedUser = userCaptor.getValue();
            assertAll(
                    () -> assertEquals(resetPasswordToken.getUser(), savedUser),
                    () -> assertEquals(encodedNewPassword, savedUser.getPassword()),
                    () -> assertTrue(savedUser.getResetPasswordTokens().isEmpty())
            );
        }

        @Test
        @DisplayName("Should throw InvalidResetPasswordTokenException when reset password token not found")
        void shouldThrowInvalidResetPasswordTokenExceptionWhenResetPasswordTokenNotFound() {
            // Given
            when(resetPasswordTokenRepository.findByToken(resetPasswordRequest.resetPasswordToken()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(
                    InvalidResetPasswordTokenException.class,
                    () -> authServiceImpl.resetPassword(resetPasswordRequest)
            );

            verify(resetPasswordTokenRepository, times(1))
                    .findByToken(resetPasswordRequest.resetPasswordToken());

            verifyNoInteractions(userRepository);
        }
    }

    @Nested
    @DisplayName("Refresh Tests")
    class RefreshTests {
        private String refreshToken;
        private JwtPair jwtPair;

        @BeforeEach
        void setUp() {
            this.refreshToken = "old_refresh_token";

            this.jwtPair = new JwtPair(
                    "access_token",
                    "refresh_token"
            );
        }

        @Test
        @DisplayName("Should refresh user jwt tokens successfully when valid request")
        void shouldRefreshUserJwtTokensSuccessfullyWhenValidRequest() {
            // Given
            when(jwtMethods.refresh(refreshToken))
                    .thenReturn(jwtPair);

            // When
            JwtPair genJwtPair = authServiceImpl.refresh(refreshToken);

            // Then
            verify(jwtMethods, times(1))
                    .refresh(refreshToken);

            assertEquals(jwtPair, genJwtPair);
        }
    }
}