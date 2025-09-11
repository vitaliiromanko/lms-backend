package com.portfolio.lmsbackend.validation.annotation;

import com.portfolio.lmsbackend.validation.validator.FillTheGapsSegmentsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FillTheGapsSegmentsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFillTheGapsSegments {
    String message() default "The number of missing text segments must be one less than the number of visible text segments";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
