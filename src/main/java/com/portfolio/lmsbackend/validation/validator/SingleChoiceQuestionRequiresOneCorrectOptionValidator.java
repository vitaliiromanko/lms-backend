package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.NewChoiceOption;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.NewSingleChoiceQuestion;
import com.portfolio.lmsbackend.validation.annotation.SingleChoiceQuestionRequiresOneCorrectOption;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SingleChoiceQuestionRequiresOneCorrectOptionValidator
        implements ConstraintValidator<SingleChoiceQuestionRequiresOneCorrectOption, NewSingleChoiceQuestion> {
    @Override
    public boolean isValid(NewSingleChoiceQuestion question, ConstraintValidatorContext context) {
        if (question == null || question.options() == null) {
            return true;
        }

        long correctCount = question.options().stream()
                .filter(NewChoiceOption::correct)
                .count();

        return correctCount == 1;
    }
}
