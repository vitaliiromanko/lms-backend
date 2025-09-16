package com.portfolio.lmsbackend.service.application.staff.admin.impl;

import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.GetAdminUserSummaryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminStaffRoleRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminUserEmailVerifiedRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.UpdateAdminUserStatusRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.user.response.*;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.token.RefreshTokenRepository;
import com.portfolio.lmsbackend.repository.user.AdminUserCriteriaRepository;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.admin.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.portfolio.lmsbackend.service.application.helper.UserServiceHelper.mapUserTo;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final AdminUserCriteriaRepository criteriaRepository;
    private final UserServiceHelper userServiceHelper;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Page<GetAdminUserSummaryResponse> getSummaries(GetAdminUserSummaryRequest getAdminUserSummaryRequest) {
        return criteriaRepository.findByCriteria(getAdminUserSummaryRequest)
                .map(user -> mapUserTo(
                        user,
                        GetAdminStaffSummaryResponse::new,
                        GetAdminStudentSummaryResponse::new
                ));
    }

    @Override
    @Transactional
    public GetAdminUserProfileResponse getProfile(UUID userId) {
        return mapUserTo(
                userServiceHelper.findByIdOrThrow(userId),
                GetAdminStaffProfileResponse::new,
                GetAdminStudentProfileResponse::new
        );
    }

    @Override
    @Transactional
    public void updateEmailVerified(UpdateAdminUserEmailVerifiedRequest updateAdminUserEmailVerifiedRequest, String header) {
        User user = userServiceHelper.findByIdOrThrow(updateAdminUserEmailVerifiedRequest.userId());
        user.setEmailVerified(updateAdminUserEmailVerifiedRequest.newEmailVerified());

        if (updateAdminUserEmailVerifiedRequest.newEmailVerified()) {
            user.getVerificationTokens().clear();
            userRepository.save(user);
        } else {
            userServiceHelper.sendVerificationToken(user, header);
        }
    }

    @Override
    public void updateStatus(UpdateAdminUserStatusRequest updateAdminUserStatusRequest) {
        User user = userServiceHelper.findByIdOrThrow(updateAdminUserStatusRequest.userId());
        user.setStatus(updateAdminUserStatusRequest.newStatus());
        userRepository.save(user);
    }

    @Override
    public void updateStaffRole(UpdateAdminStaffRoleRequest updateAdminStaffRoleRequest) {
        Staff staff = userServiceHelper.findByIdAndTypeOrThrow(updateAdminStaffRoleRequest.staffId(), Staff.class);
        staff.setRole(updateAdminStaffRoleRequest.newRole());
        userRepository.save(staff);
    }

    @Override
    public void deleteRefreshToken(UUID tokenId) {
        refreshTokenRepository.deleteById(tokenId);
    }
}
