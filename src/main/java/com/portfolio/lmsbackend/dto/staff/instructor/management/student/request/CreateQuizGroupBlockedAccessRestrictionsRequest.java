package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import java.util.Set;

public class CreateQuizGroupBlockedAccessRestrictionsRequest extends CreateQuizGroupAccessRestrictionsRequest {
    public CreateQuizGroupBlockedAccessRestrictionsRequest(String groupId, Set<String> studentIds) {
        super(groupId, studentIds);
    }
}
