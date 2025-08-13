package com.portfolio.lmsbackend.controller.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.invitation.request.StaffInvitationRequest;
import com.portfolio.lmsbackend.service.application.staff.admin.InvitationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.portfolio.lmsbackend.utils.StringsHelper.ORIGIN_HEADER;
import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/invite")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/staff")
    public ResponseEntity<String> inviteStaff(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody StaffInvitationRequest invitationRequest,
            HttpServletRequest request
    ) {
        invitationService.inviteStaff(userId, invitationRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }
}
