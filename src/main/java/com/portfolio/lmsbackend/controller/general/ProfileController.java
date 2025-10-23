package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfileDataRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePasswordRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePhotoRequest;
import com.portfolio.lmsbackend.dto.general.profile.response.GetUserProfileResponse;
import com.portfolio.lmsbackend.dto.general.profile.response.UpdateProfilePhotoResponse;
import com.portfolio.lmsbackend.service.application.general.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.ORIGIN_HEADER;
import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;

@Tag(
        name = "General / ProfileController",
        description = "Endpoints for user profile operations"
)
@Validated
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(
            summary = "Get profile information",
            description = "Endpoint to retrieve information about a user's profile."
    )
    @GetMapping
    public ResponseEntity<GetUserProfileResponse> getProfile(
            @CurrentSecurityContext(expression = "authentication.name") String userId
    ) {
        return ResponseEntity.ok().body(profileService.getProfile(UUID.fromString(userId)));
    }

    @Operation(
            summary = "Update profile photo",
            description = "Endpoint to update a user's profile photo."
    )
    @PutMapping("/update-photo")
    public ResponseEntity<UpdateProfilePhotoResponse> updatePhoto(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @ModelAttribute UpdateProfilePhotoRequest updateProfilePhotoRequest
    ) {
        return ResponseEntity.ok().body(new UpdateProfilePhotoResponse(
                profileService.updatePhoto(UUID.fromString(userId), updateProfilePhotoRequest)));
    }

    @Operation(
            summary = "Update profile data",
            description = "Endpoint to update a user's profile information."
    )
    @PutMapping("/update-data")
    public ResponseEntity<String> updateData(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody UpdateProfileDataRequest updateProfileDataRequest,
            HttpServletRequest request
    ) {
        profileService.updateData(UUID.fromString(userId), updateProfileDataRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Update password",
            description = "Endpoint to update a user's password."
    )
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody UpdateProfilePasswordRequest updateProfilePasswordRequest
    ) {
        profileService.updatePassword(UUID.fromString(userId), updateProfilePasswordRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
