package com.portfolio.lmsbackend.model.media.image;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_photo")
@DiscriminatorValue("USER_PHOTO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPhoto extends Image {
    public UserPhoto(String originalFilename, String url, String publicId) {
        super(originalFilename, url, publicId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "originalFilename = " + getOriginalFilename() + ", " +
                "url = " + getUrl() + ", " +
                "publicId = " + getPublicId() + ", " +
                "createdAt = " + getCreatedAt() + ")";
    }
}
