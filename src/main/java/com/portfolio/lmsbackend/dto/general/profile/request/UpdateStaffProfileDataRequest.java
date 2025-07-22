package com.portfolio.lmsbackend.dto.general.profile.request;

public class UpdateStaffProfileDataRequest extends UpdateProfileDataRequest {
    public UpdateStaffProfileDataRequest(
            String firstName,
            String lastName,
            String email
    ) {
        super(firstName, lastName, email);
    }
}
