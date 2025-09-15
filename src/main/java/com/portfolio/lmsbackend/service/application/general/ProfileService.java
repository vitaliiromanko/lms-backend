package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfileDataRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePasswordRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePhotoRequest;
import com.portfolio.lmsbackend.dto.general.profile.response.GetUserProfileResponse;

import java.util.UUID;

public interface ProfileService {
    GetUserProfileResponse getProfile(UUID userId);

    String updatePhoto(UUID userId, UpdateProfilePhotoRequest updateProfilePhotoRequest);

    void updateData(UUID userId, UpdateProfileDataRequest updateProfileDataRequest, String header);

    void updatePassword(UUID userId, UpdateProfilePasswordRequest updateProfilePasswordRequest);
}
