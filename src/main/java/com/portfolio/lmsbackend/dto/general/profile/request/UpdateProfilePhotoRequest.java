package com.portfolio.lmsbackend.dto.general.profile.request;

import com.portfolio.lmsbackend.validation.annotation.ValidImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public record UpdateProfilePhotoRequest(
        @NotNull
        @ValidImage
        @RequestPart("photo")
        MultipartFile photo
) {
}
