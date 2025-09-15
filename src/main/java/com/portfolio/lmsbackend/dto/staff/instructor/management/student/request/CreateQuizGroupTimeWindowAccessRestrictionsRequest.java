package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.validation.annotation.ValidTimeWindow;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@ValidTimeWindow
public class CreateQuizGroupTimeWindowAccessRestrictionsRequest extends CreateQuizGroupAccessRestrictionsRequest {
    @NotNull
    private final LocalDateTime availableFrom;

    @NotNull
    private final LocalDateTime availableUntil;

    @JsonCreator
    public CreateQuizGroupTimeWindowAccessRestrictionsRequest(
            @JsonProperty("group_id") UUID groupId,
            @JsonProperty("student_ids") Set<UUID> studentIds,
            @JsonProperty("available_from") LocalDateTime availableFrom,
            @JsonProperty("available_until") LocalDateTime availableUntil
    ) {
        super(groupId, studentIds);
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
    }

    @JsonProperty("available_from")
    public LocalDateTime availableFrom() {
        return availableFrom;
    }

    @JsonProperty("available_until")
    public LocalDateTime availableUntil() {
        return availableUntil;
    }
}
