package com.portfolio.lmsbackend.validation.annotation;

import com.portfolio.lmsbackend.validation.validator.TimeWindowValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeWindowValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeWindow {
    String message() default "availableUntil must be after availableFrom and in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
