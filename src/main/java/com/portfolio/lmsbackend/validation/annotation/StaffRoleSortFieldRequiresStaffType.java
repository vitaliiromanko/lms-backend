package com.portfolio.lmsbackend.validation.annotation;

import com.portfolio.lmsbackend.validation.validator.StaffRoleSortFieldRequiresStaffTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StaffRoleSortFieldRequiresStaffTypeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StaffRoleSortFieldRequiresStaffType {
    String message() default "sortField STAFF_ROLE is only allowed when type is STAFF";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
