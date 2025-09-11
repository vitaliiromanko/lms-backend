package com.portfolio.lmsbackend.dto.staff.instructor.management.student.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.SearchRequest;
import com.portfolio.lmsbackend.enums.content.quiz.QuizGroupAccessRestrictionType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Sort;

public class GetQuizGroupAccessRestrictionsRequest extends SearchRequest {
    @NotBlank
    private final String groupId;

    private final QuizGroupAccessRestrictionType type;

    @JsonCreator
    public GetQuizGroupAccessRestrictionsRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search,
            @JsonProperty("group_id") String groupId,
            @JsonProperty("type") QuizGroupAccessRestrictionType type
    ) {
        super(pageNumber, pageSize, sortDirection, search);
        this.groupId = groupId;
        this.type = type;
    }

    @JsonProperty("group_id")
    public String groupId() {
        return groupId;
    }

    @JsonProperty("type")
    public QuizGroupAccessRestrictionType type() {
        return type;
    }
}
