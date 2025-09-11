package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.NewChoiceOption;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.NewMultipleChoiceQuestion;
import com.portfolio.lmsbackend.validation.annotation.MultipleChoiceQuestionRequiresMinOneCorrectOption;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipleChoiceQuestionRequiresMinOneCorrectOptionValidator
        implements ConstraintValidator<MultipleChoiceQuestionRequiresMinOneCorrectOption, NewMultipleChoiceQuestion> {
    @Override
    public boolean isValid(NewMultipleChoiceQuestion question, ConstraintValidatorContext context) {
        if (question == null || question.options() == null) {
            return true;
        }

        long correctCount = question.options().stream()
                .filter(NewChoiceOption::correct)
                .count();

        return correctCount >= 1;
    }
}
