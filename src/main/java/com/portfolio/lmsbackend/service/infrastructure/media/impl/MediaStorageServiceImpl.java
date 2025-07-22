package com.portfolio.lmsbackend.service.infrastructure.media.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.portfolio.lmsbackend.exception.media.MediaDeleteException;
import com.portfolio.lmsbackend.exception.media.MediaUploadException;
import com.portfolio.lmsbackend.service.infrastructure.media.MediaStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MediaStorageServiceImpl implements MediaStorageService {
    private final Cloudinary cloudinary;

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> upload(MultipartFile file, String folder, Transformation<?> transformation) {
        Map<String, Object> options = new HashMap<>();

        if (folder != null && !folder.isBlank()) {
            options.put("folder", folder);
        }

        if (transformation != null) {
            options.put("transformation", transformation);
        }

        options.put("overwrite", true);

        try {
            return cloudinary.uploader().upload(file.getBytes(), options);
        } catch (IOException e) {
            throw new MediaUploadException();
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new MediaDeleteException();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteFolder(String folder) {
        try {
            ApiResponse response = cloudinary.api().deleteResourcesByPrefix(folder + "/", ObjectUtils.emptyMap());
            Map<String, String> deleted = (Map<String, String>) response.get("deleted");

            boolean anythingDeleted = deleted != null && deleted.values().stream().anyMatch("deleted"::equals);

            if (anythingDeleted) {
                cloudinary.api().deleteFolder(folder, ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            throw new MediaDeleteException();
        }
    }
}
