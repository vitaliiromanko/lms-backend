package com.portfolio.lmsbackend.validation.annotation;

import com.portfolio.lmsbackend.validation.validator.SingleChoiceQuestionRequiresOneCorrectOptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SingleChoiceQuestionRequiresOneCorrectOptionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleChoiceQuestionRequiresOneCorrectOption {
    String message() default "Single choice question must have exactly one correct option";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
