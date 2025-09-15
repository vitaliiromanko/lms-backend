package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.SearchRequest;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public class GetQuizGroupAccessRestrictionsRequest extends SearchRequest {
    @NotNull
    private final UUID groupId;

    private final QuizGroupAccessRestrictionType type;

    @JsonCreator
    public GetQuizGroupAccessRestrictionsRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search,
            @JsonProperty("group_id") UUID groupId,
            @JsonProperty("type") QuizGroupAccessRestrictionType type
    ) {
        super(pageNumber, pageSize, sortDirection, search);
        this.groupId = groupId;
        this.type = type;
    }

    @JsonProperty("group_id")
    public UUID groupId() {
        return groupId;
    }

    @JsonProperty("type")
    public QuizGroupAccessRestrictionType type() {
        return type;
    }
}
