package com.portfolio.lmsbackend.dto.staff.admin.management.user.response;

import com.portfolio.lmsbackend.model.user.Student;

public class GetAdminStudentProfileResponse extends GetAdminUserProfileResponse {
    public GetAdminStudentProfileResponse(Student student) {
        super(student);
    }
}
