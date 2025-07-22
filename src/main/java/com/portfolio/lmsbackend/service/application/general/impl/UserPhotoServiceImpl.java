package com.portfolio.lmsbackend.service.application.general.impl;

import com.cloudinary.Transformation;
import com.portfolio.lmsbackend.model.media.image.UserPhoto;
import com.portfolio.lmsbackend.service.application.general.UserPhotoService;
import com.portfolio.lmsbackend.service.infrastructure.media.MediaStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPhotoServiceImpl implements UserPhotoService {
    private final MediaStorageService mediaStorageService;

    private static final String USER_PHOTO_FOLDER_PATTERN = "user/%s/photo";

    @Override
    public UserPhoto saveUserPhoto(MultipartFile photo, UUID userId, String oldPhotoPublicId) {
        String userPhotoFolder = USER_PHOTO_FOLDER_PATTERN.formatted(userId.toString());
        if (oldPhotoPublicId != null) {
            mediaStorageService.delete(oldPhotoPublicId);
        }

        Transformation<?> squareTransformation = new Transformation<>()
                .crop("crop")
                .aspectRatio("1:1")
                .gravity("center");


        Map<String, Object> uploadResult = mediaStorageService.upload(photo, userPhotoFolder, squareTransformation);
        return new UserPhoto(
                photo.getOriginalFilename(),
                (String) uploadResult.get("secure_url"),
                (String) uploadResult.get("public_id")
        );
    }
}
