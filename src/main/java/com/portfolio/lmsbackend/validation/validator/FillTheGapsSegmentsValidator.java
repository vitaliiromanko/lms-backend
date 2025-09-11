package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.NewFillTheGapsQuestion;
import com.portfolio.lmsbackend.validation.annotation.ValidFillTheGapsSegments;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FillTheGapsSegmentsValidator
        implements ConstraintValidator<ValidFillTheGapsSegments, NewFillTheGapsQuestion> {
    @Override
    public boolean isValid(NewFillTheGapsQuestion question, ConstraintValidatorContext context) {
        if (question == null || question.visibleTextSegments() == null || question.missingTextSegments() == null) {
            return true;
        }

        return question.missingTextSegments().size() == (question.visibleTextSegments().size() - 1);
    }
}
