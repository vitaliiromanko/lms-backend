package com.portfolio.lmsbackend.model.media.image;

import com.portfolio.lmsbackend.model.media.BaseMediaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "image_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Image extends BaseMediaEntity {
    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    protected Image(String originalFilename, String url, String publicId) {
        this.originalFilename = originalFilename;
        this.url = url;
        this.publicId = publicId;
    }
}
