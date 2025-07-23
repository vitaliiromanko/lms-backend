package com.portfolio.lmsbackend.dto.general.profile.response;

import com.portfolio.lmsbackend.model.user.Student;

public class GetStudentProfileResponse extends GetUserProfileResponse {
    public GetStudentProfileResponse(Student student) {
        super(student);
    }
}
