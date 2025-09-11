package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.CreateQuizGroupTimeWindowAccessRestrictionsRequest;
import com.portfolio.lmsbackend.validation.annotation.ValidTimeWindow;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class TimeWindowValidator
        implements ConstraintValidator<ValidTimeWindow, CreateQuizGroupTimeWindowAccessRestrictionsRequest> {
    @Override
    public boolean isValid(CreateQuizGroupTimeWindowAccessRestrictionsRequest value,
                           ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDateTime from = value.availableFrom();
        LocalDateTime until = value.availableUntil();

        if (from == null || until == null) {
            return true;
        }

        return until.isAfter(from) && until.isAfter(LocalDateTime.now());
    }
}
