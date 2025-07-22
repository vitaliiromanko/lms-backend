package com.portfolio.lmsbackend.dto.general.profile.request;

public class UpdateStudentProfileDataRequest extends UpdateProfileDataRequest {
    public UpdateStudentProfileDataRequest(
            String firstName,
            String lastName,
            String email
    ) {
        super(firstName, lastName, email);
    }
}
