package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import java.util.Set;
import java.util.UUID;

public class CreateQuizGroupBlockedAccessRestrictionsRequest extends CreateQuizGroupAccessRestrictionsRequest {
    public CreateQuizGroupBlockedAccessRestrictionsRequest(UUID groupId, Set<UUID> studentIds) {
        super(groupId, studentIds);
    }
}
