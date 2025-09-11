package com.portfolio.lmsbackend.validation.annotation;

import com.portfolio.lmsbackend.validation.validator.MultipleChoiceQuestionRequiresMinOneCorrectOptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MultipleChoiceQuestionRequiresMinOneCorrectOptionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipleChoiceQuestionRequiresMinOneCorrectOption {
    String message() default "Multiple choice question must have min one correct option";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
