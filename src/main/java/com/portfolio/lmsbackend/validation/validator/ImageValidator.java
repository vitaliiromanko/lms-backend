package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.validation.annotation.ValidImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public class ImageValidator implements ConstraintValidator<ValidImage, Object> {
    private long maxSize;
    private String[] allowedTypes;

    @Override
    public void initialize(ValidImage constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        if (value instanceof MultipartFile) {
            return isImageValid((MultipartFile) value, constraintValidatorContext);
        }

        if (value instanceof Collection<?>) {
            if (((Collection<?>) value).isEmpty()) {
                return true;
            }

            for (Object item : (Collection<?>) value) {
                if (item instanceof MultipartFile &&
                        !isImageValid((MultipartFile) item, constraintValidatorContext)) {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean isImageValid(MultipartFile image, ConstraintValidatorContext context) {
        if (image.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Image size exceeds maximum allowed size of " + maxSize + " bytes")
                    .addConstraintViolation();
            return false;
        }

        String contentType = image.getContentType();
        if (allowedTypes.length > 0 && (contentType == null || !isAllowedContentType(contentType))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid image type. Allowed types are: " + String.join(", ", allowedTypes))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isAllowedContentType(String contentType) {
        for (String allowedType : allowedTypes) {
            if (allowedType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }
}
