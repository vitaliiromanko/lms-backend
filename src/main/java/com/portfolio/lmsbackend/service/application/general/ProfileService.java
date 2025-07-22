package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePasswordRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateProfilePhotoRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateStaffProfileDataRequest;
import com.portfolio.lmsbackend.dto.general.profile.request.UpdateStudentProfileDataRequest;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStaffProfileResponse;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStudentProfileResponse;

public interface ProfileService {
    GetStaffProfileResponse getStaffProfile(String userId);

    GetStudentProfileResponse getStudentProfile(String userId);

    String updatePhoto(String userId, UpdateProfilePhotoRequest updateProfilePhotoRequest);

    void updateStaffData(String userId, UpdateStaffProfileDataRequest updateProfileDataRequest, String header);

    void updateStudentData(String userId, UpdateStudentProfileDataRequest updateProfileDataRequest, String header);

    void updatePassword(String userId, UpdateProfilePasswordRequest updateProfilePasswordRequest);
}
