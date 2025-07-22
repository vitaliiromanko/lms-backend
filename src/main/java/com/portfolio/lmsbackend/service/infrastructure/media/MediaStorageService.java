package com.portfolio.lmsbackend.service.infrastructure.media;

import com.cloudinary.Transformation;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface MediaStorageService {
    Map<String, Object> upload(MultipartFile file, String folder, Transformation<?> transformation);

    void delete(String publicId);

    void deleteFolder(String folder);
}
