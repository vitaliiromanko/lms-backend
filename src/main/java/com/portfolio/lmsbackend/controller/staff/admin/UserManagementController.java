package com.portfolio.lmsbackend.controller.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.GetAdminUserSummaryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminStaffRoleRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminUserEmailVerifiedRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminUserStatusRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.response.GetAdminUserProfileResponse;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.response.GetAdminUserSummaryResponse;
import com.portfolio.lmsbackend.service.application.staff.admin.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.ORIGIN_HEADER;
import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;

@Tag(
        name = "Administrator / UserManagementController",
        description = "Endpoints for managing users"
)
@RestController
@RequestMapping("/user-profiles/manage")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;

    @Operation(
            summary = "Get user summaries",
            description = "Endpoint to retrieve a page of brief information about users."
    )
    @GetMapping
    public ResponseEntity<Page<GetAdminUserSummaryResponse>> getSummaries(
            @Valid @RequestBody GetAdminUserSummaryRequest getAdminUserSummaryRequest
    ) {
        return ResponseEntity.ok().body(userManagementService.getSummaries(getAdminUserSummaryRequest));
    }

    @Operation(
            summary = "Get profile information",
            description = "Endpoint to retrieve information about a user's profile."
    )
    @GetMapping("/{userId}")
    public ResponseEntity<GetAdminUserProfileResponse> getProfile(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok().body(userManagementService.getProfile(userId));
    }

    @Operation(
            summary = "Update email verification status",
            description = "Endpoint to update a user's email verification status."
    )
    @PutMapping("/update-email-verified")
    public ResponseEntity<String> updateEmailVerified(
            @Valid @RequestBody UpdateAdminUserEmailVerifiedRequest updateAdminUserEmailVerifiedRequest,
            HttpServletRequest request
    ) {
        userManagementService.updateEmailVerified(updateAdminUserEmailVerifiedRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Update user status",
            description = "Endpoint to update user status."
    )
    @PutMapping("/update-status")
    public ResponseEntity<String> updateStatus(
            @Valid @RequestBody UpdateAdminUserStatusRequest updateAdminUserStatusRequest
    ) {
        userManagementService.updateStatus(updateAdminUserStatusRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Update staff role",
            description = "Endpoint to update staff role."
    )
    @PutMapping("/update-staff-role")
    public ResponseEntity<String> updateStaffRole(
            @Valid @RequestBody UpdateAdminStaffRoleRequest updateAdminStaffRoleRequest
    ) {
        userManagementService.updateStaffRole(updateAdminStaffRoleRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Delete refresh token",
            description = "Endpoint to delete a user's refresh token."
    )
    @DeleteMapping("/delete-refresh-token/{tokenId}")
    public ResponseEntity<String> deleteRefreshToken(
            @PathVariable UUID tokenId
    ) {
        userManagementService.deleteRefreshToken(tokenId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
