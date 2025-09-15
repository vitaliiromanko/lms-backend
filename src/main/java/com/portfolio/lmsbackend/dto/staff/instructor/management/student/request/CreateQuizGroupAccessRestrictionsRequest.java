package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateQuizGroupBlockedAccessRestrictionsRequest.class, name = "BLOCKED"),
        @JsonSubTypes.Type(value = CreateQuizGroupTimeWindowAccessRestrictionsRequest.class, name = "TIME_WINDOW")
})
public abstract class CreateQuizGroupAccessRestrictionsRequest {
    @NotNull
    private final UUID groupId;

    @NotEmpty
    private final Set<@NotNull UUID> studentIds;

    @JsonCreator
    protected CreateQuizGroupAccessRestrictionsRequest(
            @JsonProperty("group_id") UUID groupId,
            @JsonProperty("student_ids") Set<UUID> studentIds
    ) {
        this.groupId = groupId;
        this.studentIds = studentIds;
    }

    @JsonProperty("group_id")
    public UUID groupId() {
        return groupId;
    }

    @JsonProperty("student_ids")
    public Set<UUID> studentIds() {
        return studentIds;
    }
}
