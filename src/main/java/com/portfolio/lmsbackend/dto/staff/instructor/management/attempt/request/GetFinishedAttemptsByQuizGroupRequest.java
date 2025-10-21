package com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.SearchRequest;
import com.portfolio.lmsbackend.enums.content.quiz.FinishedAttemptSortField;
import com.portfolio.lmsbackend.enums.content.quiz.FinishedAttemptStatus;
import com.portfolio.lmsbackend.enums.user.UserType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public class GetFinishedAttemptsByQuizGroupRequest extends SearchRequest {
    @NotNull
    private final UUID quizGroupId;

    private final FinishedAttemptSortField sortField;

    private final FinishedAttemptStatus status;

    private final UserType performerType;

    @JsonCreator
    public GetFinishedAttemptsByQuizGroupRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_field") FinishedAttemptSortField sortField,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search,
            @JsonProperty("status") FinishedAttemptStatus status,
            @JsonProperty("performer_type") UserType performerType,
            @JsonProperty("quiz_group_id") UUID quizGroupId
    ) {
        super(pageNumber, pageSize, sortDirection, search);
        this.quizGroupId = quizGroupId;
        this.sortField = sortField != null ? sortField : FinishedAttemptSortField.FINISHED_AT;
        this.status = status;
        this.performerType = performerType;
    }

    @JsonProperty("quiz_group_id")
    public UUID quizGroupId() {
        return quizGroupId;
    }

    @JsonProperty("sort_field")
    public FinishedAttemptSortField sortField() {
        return sortField;
    }

    @JsonProperty("status")
    public FinishedAttemptStatus status() {
        return status;
    }

    @JsonProperty("performer_type")
    public UserType performerType() {
        return performerType;
    }
}
