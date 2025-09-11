package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateQuizGroupBlockedAccessRestrictionsRequest.class, name = "BLOCKED"),
        @JsonSubTypes.Type(value = CreateQuizGroupTimeWindowAccessRestrictionsRequest.class, name = "TIME_WINDOW")
})
public abstract class CreateQuizGroupAccessRestrictionsRequest {
    @NotBlank
    private final String groupId;

    @NotEmpty
    private final Set<@NotBlank String> studentIds;

    @JsonCreator
    protected CreateQuizGroupAccessRestrictionsRequest(
            @JsonProperty("group_id") String groupId,
            @JsonProperty("student_ids") Set<String> studentIds
    ) {
        this.groupId = groupId;
        this.studentIds = studentIds;
    }

    @JsonProperty("group_id")
    public String groupId() {
        return groupId;
    }

    @JsonProperty("student_ids")
    public Set<String> studentIds() {
        return studentIds;
    }
}
