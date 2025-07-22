package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePasswordRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePhotoRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateStaffProfileDataRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateStudentProfileDataRequest;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStaffProfileResponse;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStudentProfileResponse;
import com.portfolio.lmsbackend.dto.general.profile.response.UpdateProfilePhotoResponse;
import com.portfolio.lmsbackend.service.application.general.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.portfolio.lmsbackend.utils.StringsHelper.ORIGIN_HEADER;
import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;

@Validated
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ProfileController {
    private final ProfileService profileService;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/staff")
    public ResponseEntity<GetStaffProfileResponse> getStaffProfile(
            @CurrentSecurityContext(expression = "authentication.name") String userId
    ) {
        return ResponseEntity.ok().body(profileService.getStaffProfile(userId));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student")
    public ResponseEntity<GetStudentProfileResponse> getStudentProfile(
            @CurrentSecurityContext(expression = "authentication.name") String userId
    ) {
        return ResponseEntity.ok().body(profileService.getStudentProfile(userId));
    }

    @PutMapping("/update-photo")
    public ResponseEntity<UpdateProfilePhotoResponse> updatePhoto(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @ModelAttribute UpdateProfilePhotoRequest updateProfilePhotoRequest
    ) {
        return ResponseEntity.ok().body(new UpdateProfilePhotoResponse(
                profileService.updatePhoto(userId, updateProfilePhotoRequest)));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/staff/update-data")
    public ResponseEntity<String> updateStaffData(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody UpdateStaffProfileDataRequest updateProfileDataRequest,
            HttpServletRequest request
    ) {
        profileService.updateStaffData(userId, updateProfileDataRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/student/update-data")
    public ResponseEntity<String> updateStudentData(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody UpdateStudentProfileDataRequest updateProfileDataRequest,
            HttpServletRequest request
    ) {
        profileService.updateStudentData(userId, updateProfileDataRequest, request.getHeader(ORIGIN_HEADER));
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @CurrentSecurityContext(expression = "authentication.name") String userId,
            @Valid @RequestBody UpdateProfilePasswordRequest updateProfilePasswordRequest
    ) {
        profileService.updatePassword(userId, updateProfilePasswordRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
