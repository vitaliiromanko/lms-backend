package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.GetAdminUserSummaryRequest;
import com.portfolio.lmsbackend.enums.user.UserSortField;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.validation.annotation.StaffRoleSortFieldRequiresStaffType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StaffRoleSortFieldRequiresStaffTypeValidator implements ConstraintValidator<StaffRoleSortFieldRequiresStaffType, GetAdminUserSummaryRequest> {
    @Override
    public boolean isValid(GetAdminUserSummaryRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        UserType type = request.type();
        UserSortField sortField = request.sortField();

        if (UserSortField.STAFF_ROLE == sortField && type != UserType.STAFF) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("sortField")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

