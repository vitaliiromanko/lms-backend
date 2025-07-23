package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfileDataRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePasswordRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePhotoRequest;
import com.portfolio.lmsbackend.dto.general.profile.response.GetUserProfileResponse;

public interface ProfileService {
    GetUserProfileResponse getProfile(String userId);

    String updatePhoto(String userId, UpdateProfilePhotoRequest updateProfilePhotoRequest);

    void updateData(String userId, UpdateProfileDataRequest updateProfileDataRequest, String header);

    void updatePassword(String userId, UpdateProfilePasswordRequest updateProfilePasswordRequest);
}
