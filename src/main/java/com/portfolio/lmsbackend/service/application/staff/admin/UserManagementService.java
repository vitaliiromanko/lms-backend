package com.portfolio.lmsbackend.service.application.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.GetAdminUserSummaryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminStaffRoleRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminUserEmailVerifiedRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminUserStatusRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.response.GetAdminUserProfileResponse;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.response.GetAdminUserSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserManagementService {
    Page<GetAdminUserSummaryResponse> getSummaries(GetAdminUserSummaryRequest getAdminUserSummaryRequest);

    GetAdminUserProfileResponse getProfile(UUID userId);

    void updateEmailVerified(UpdateAdminUserEmailVerifiedRequest updateAdminUserEmailVerifiedRequest, String header);

    void updateStatus(UpdateAdminUserStatusRequest updateAdminUserStatusRequest);

    void updateStaffRole(UpdateAdminStaffRoleRequest updateAdminStaffRoleRequest);

    void deleteRefreshToken(UUID tokenId);
}
