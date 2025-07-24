package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.GetAdminUserSummaryRequest;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.validation.annotation.StaffRoleRequiresStaffType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StaffRoleRequiresStaffTypeValidator implements ConstraintValidator<StaffRoleRequiresStaffType, GetAdminUserSummaryRequest> {
    @Override
    public boolean isValid(GetAdminUserSummaryRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        StaffRole staffRole = request.staffRole();
        UserType type = request.type();

        if (staffRole != null && type != UserType.STAFF) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("staffRole")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
