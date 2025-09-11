package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record DeleteQuizGroupAccessRestrictionsRequest(
        @NotBlank
        @JsonProperty("group_id")
        String groupId,
        @NotEmpty
        @JsonProperty("student_ids")
        Set<@NotBlank String> studentIds
) {
}
