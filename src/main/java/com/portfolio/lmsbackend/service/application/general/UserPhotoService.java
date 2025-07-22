package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.model.media.image.UserPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserPhotoService {
    UserPhoto saveUserPhoto(MultipartFile photo, UUID userId, String oldPhotoPublicId);
}
