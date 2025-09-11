package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.validation.annotation.NonNegativeDuration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;

public class NonNegativeDurationValidator implements ConstraintValidator<NonNegativeDuration, Duration> {
    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return value == null || !value.isNegative();
    }
}
