package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.profile.request.*;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStaffProfileResponse;
import com.portfolio.lmsbackend.dto.general.profile.response.GetStudentProfileResponse;
import com.portfolio.lmsbackend.dto.general.profile.response.GetUserProfileResponse;
import com.portfolio.lmsbackend.exception.user.EmailAlreadyUsedException;
import com.portfolio.lmsbackend.exception.user.InvalidOldPasswordException;
import com.portfolio.lmsbackend.exception.user.InvalidUserTypeException;
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

import static com.portfolio.lmsbackend.service.application.general.UserServiceHelper.unexpectedUserType;

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
    public GetUserProfileResponse getProfile(String userId) {
        return userServiceHelper.mapUserTo(
                userServiceHelper.findByIdAndTypeOrThrow(userId, User.class),
                GetStaffProfileResponse::new,
                GetStudentProfileResponse::new
        );
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
    public void updateData(String userId, UpdateProfileDataRequest updateProfileDataRequest, String header) {
        User user = userServiceHelper.findByIdAndTypeOrThrow(userId, User.class);
        switch (user) {
            case Staff staff ->
                    updateStaffData(staff, cast(updateProfileDataRequest, UpdateStaffProfileDataRequest.class), header);
            case Student student ->
                    updateStudentData(student, cast(updateProfileDataRequest, UpdateStudentProfileDataRequest.class), header);
            default -> throw unexpectedUserType(user);
        }
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

    private <T extends UpdateProfileDataRequest> T cast(UpdateProfileDataRequest updateProfileDataRequest, Class<T> child) {
        try {
            return child.cast(updateProfileDataRequest);
        } catch (ClassCastException ex) {
            throw new InvalidUserTypeException();
        }
    }

    private void updateStaffData(Staff staff, UpdateStaffProfileDataRequest updateProfileDataRequest, String header) {
        UpdateUserResult updateUserResult = updateUserData(staff, updateProfileDataRequest);

        boolean dataUpdated = updateUserResult.dataUpdated;
        boolean emailUpdated = updateUserResult.emailUpdated;

        finalizeProfileUpdate(staff, new UpdateUserResult(dataUpdated, emailUpdated), header);
    }

    private void updateStudentData(Student student, UpdateStudentProfileDataRequest updateProfileDataRequest, String header) {
        UpdateUserResult updateUserResult = updateUserData(student, updateProfileDataRequest);

        boolean dataUpdated = updateUserResult.dataUpdated;
        boolean emailUpdated = updateUserResult.emailUpdated;

        finalizeProfileUpdate(student, new UpdateUserResult(dataUpdated, emailUpdated), header);
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
