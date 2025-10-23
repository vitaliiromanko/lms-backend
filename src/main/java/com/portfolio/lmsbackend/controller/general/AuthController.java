package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.auth.request.*;
import com.portfolio.lmsbackend.dto.general.auth.response.LoginResponse;
import com.portfolio.lmsbackend.dto.general.auth.response.RefreshResponse;
import com.portfolio.lmsbackend.dto.general.auth.response.VerifyResponse;
import com.portfolio.lmsbackend.security.general.JwtPair;
import com.portfolio.lmsbackend.service.application.general.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import static com.portfolio.lmsbackend.utils.StringsHelper.ORIGIN_HEADER;
import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(
        name = "General / AuthController",
        description = "Endpoints for handling all user authentication operations"
)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${application.security.jwt.refresh-token.lifetime}")
    private Long refreshTokenLifetime;

    private static final String COOKIE_NAME_FOR_REFRESH = "lms_refresh_token";

    @Operation(
            summary = "Register staff member",
            description = "Endpoint to register a staff member."
    )
    @PostMapping("/staff/register")
    public ResponseEntity<String> registerStaff(
            @Valid @RequestBody StaffRegisterRequest registerRequest
    ) {
        authService.registerStaff(registerRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Register student",
            description = "Endpoint to register a student."
    )
    @PostMapping("/student/register")
    public ResponseEntity<String> registerStudent(
            @Valid @RequestBody StudentRegisterRequest registerRequest,
            HttpServletRequest request
    ) {
        authService.registerStudent(registerRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Send new verification token",
            description = "Endpoint to send a new verification token to a user's email."
    )
    @PostMapping("/new-verification-token")
    public ResponseEntity<String> sendNewVerificationToken(
            @Valid @RequestBody NewVerificationTokenRequest newVerificationTokenRequest,
            HttpServletRequest request
    ) {
        authService.sendNewVerificationToken(newVerificationTokenRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Verify email",
            description = "Endpoint to verify a user's email."
    )
    @PostMapping("/verify")
    public ResponseEntity<VerifyResponse> verify(
            @Valid @RequestBody VerifyRequest verifyRequest,
            HttpServletResponse response
    ) {
        JwtPair jwtPair = authService.verify(verifyRequest);
        createRefreshCookie(response, jwtPair.refreshToken());
        return ResponseEntity.status(CREATED).body(new VerifyResponse(jwtPair.accessToken()));
    }

    @Operation(
            summary = "Login",
            description = "Endpoint for user authentication in the system."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        JwtPair jwtPair = authService.login(loginRequest);
        createRefreshCookie(response, jwtPair.refreshToken());
        return ResponseEntity.status(CREATED).body(new LoginResponse(jwtPair.accessToken()));
    }

    @Operation(
            summary = "Logout",
            description = "Endpoint for user logout from the system."
    )
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue(COOKIE_NAME_FOR_REFRESH) String refreshToken,
            HttpServletResponse response
    ) {
        removeRefreshCookie(response);
        authService.logout(refreshToken);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Send reset password token",
            description = "Endpoint to send a reset password token to a user's email."
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendResetPasswordToken(
            @Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest,
            HttpServletRequest request
    ) {
        authService.sendResetPasswordToken(forgotPasswordRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Reset password",
            description = "Endpoint to reset a user's password."
    )
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Refresh access token",
            description = "Endpoint to refresh a user's access token."
    )
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @CookieValue(COOKIE_NAME_FOR_REFRESH) String refreshToken,
            HttpServletResponse response
    ) {
        JwtPair jwtPair = authService.refresh(refreshToken);
        createRefreshCookie(response, jwtPair.refreshToken());
        return ResponseEntity.ok().body(new RefreshResponse(jwtPair.accessToken()));
    }


    private void createRefreshCookie(HttpServletResponse response, String refreshToken) {
        addRefreshCookie(response, refreshToken, refreshTokenLifetime);
    }

    private void removeRefreshCookie(HttpServletResponse response) {
        addRefreshCookie(response, "", 0L);
    }

    private static void addRefreshCookie(HttpServletResponse response, String value, Long maxAge) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME_FOR_REFRESH, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("none")
                .maxAge(Duration.ofMinutes(maxAge))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
