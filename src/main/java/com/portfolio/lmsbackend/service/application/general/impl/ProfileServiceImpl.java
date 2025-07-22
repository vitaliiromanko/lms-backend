package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.profile.request.*;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStaffProfileResponse;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStudentProfileResponse;
import com.portfolio.lmsbackend.exception.user.EmailAlreadyUsedException;
import com.portfolio.lmsbackend.exception.user.InvalidOldPasswordException;
import com.portfolio.lmsbackend.model.media.image.UserPhoto;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import com.portfolio.lmsbackend.service.application.general.ProfileService;
import com.portfolio.lmsbackend.service.application.general.UserPhotoService;
import com.portfolio.lmsbackend.service.application.general.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final UserPhotoService userPhotoService;
    private final UserServiceHelper userServiceHelper;
    private final PasswordEncoder passwordEncoder;

    public record UpdateUserResult(boolean dataUpdated, boolean emailUpdated) {
    }

    @Override
    @Transactional(readOnly = true)
    public GetStaffProfileResponse getStaffProfile(String userId) {
        return new GetStaffProfileResponse(userServiceHelper.findByIdAndTypeOrThrow(userId, Staff.class));
    }

    @Override
    @Transactional(readOnly = true)
    public GetStudentProfileResponse getStudentProfile(String userId) {
        return new GetStudentProfileResponse(userServiceHelper.findByIdAndTypeOrThrow(userId, Student.class));
    }

    @Override
    @Transactional
    public String updatePhoto(String userId, UpdateProfilePhotoRequest updateProfilePhotoRequest) {
        User user = userServiceHelper.findByIdAndTypeOrThrow(userId, User.class);

        UserPhoto userPhoto = userPhotoService.saveUserPhoto(
                updateProfilePhotoRequest.photo(),
                user.getId(),
                user.getPhoto() != null ? user.getPhoto().getPublicId() : null
        );

        user.setPhoto(userPhoto);
        userRepository.save(user);
        return userPhoto.getUrl();
    }

    @Override
    @Transactional
    public void updateStaffData(String userId, UpdateStaffProfileDataRequest updateProfileDataRequest, String header) {
        Staff staff = userServiceHelper.findByIdAndTypeOrThrow(userId, Staff.class);
        UpdateUserResult updateUserResult = updateUserData(staff, updateProfileDataRequest);

        boolean dataUpdated = updateUserResult.dataUpdated;
        boolean emailUpdated = updateUserResult.emailUpdated;

        finalizeProfileUpdate(staff, new UpdateUserResult(dataUpdated, emailUpdated), header);
    }

    @Override
    @Transactional
    public void updateStudentData(String userId, UpdateStudentProfileDataRequest updateProfileDataRequest, String header) {
        Student student = userServiceHelper.findByIdAndTypeOrThrow(userId, Student.class);
        UpdateUserResult updateUserResult = updateUserData(student, updateProfileDataRequest);

        boolean dataUpdated = updateUserResult.dataUpdated;
        boolean emailUpdated = updateUserResult.emailUpdated;

        finalizeProfileUpdate(student, new UpdateUserResult(dataUpdated, emailUpdated), header);
    }

    @Override
    public void updatePassword(String userId, UpdateProfilePasswordRequest updateProfilePasswordRequest) {
        User user = userServiceHelper.findByIdAndTypeOrThrow(userId, User.class);

        if (!passwordEncoder.matches(updateProfilePasswordRequest.oldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException();
        }

        user.setPassword(passwordEncoder.encode(updateProfilePasswordRequest.newPassword()));
        userRepository.save(user);
    }

    private UpdateUserResult updateUserData(User user, UpdateProfileDataRequest updateProfileDataRequest) {
        boolean dataUpdated = false;
        boolean emailUpdated = false;

        if (!user.getFirstName().equals(updateProfileDataRequest.firstName())) {
            user.setFirstName(updateProfileDataRequest.firstName());
            dataUpdated = true;
        }

        if (!user.getLastName().equals(updateProfileDataRequest.lastName())) {
            user.setLastName(updateProfileDataRequest.lastName());
            dataUpdated = true;
        }

        if (!user.getEmail().equals(updateProfileDataRequest.email())) {
            if (userRepository.existsByEmail(updateProfileDataRequest.email())) {
                throw new EmailAlreadyUsedException();
            }

            user.setEmail(updateProfileDataRequest.email());
            user.setEmailVerified(false);
            user.getVerificationTokens().clear();
            dataUpdated = true;
            emailUpdated = true;
        }

        return new UpdateUserResult(dataUpdated, emailUpdated);
    }

    private void finalizeProfileUpdate(User user, UpdateUserResult updateUserResult, String header) {
        if (updateUserResult.emailUpdated) {
            userServiceHelper.sendVerificationToken(user, header);
        } else if (updateUserResult.dataUpdated) {
            userRepository.save(user);
        }
    }
}
