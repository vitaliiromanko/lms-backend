package com.portfolio.lmsbackend.validation.annotation;

import com.portfolio.lmsbackend.validation.validator.UniqueMissingTextSegmentIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueMissingTextSegmentIdValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueMissingTextSegmentId {
    String message() default "Duplicate missingTextSegmentId in gapAnswerSegments";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
