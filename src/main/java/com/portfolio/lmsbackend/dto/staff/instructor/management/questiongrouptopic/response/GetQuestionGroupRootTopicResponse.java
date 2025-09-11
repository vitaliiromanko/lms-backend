package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response.QuestionGroupSummary;

import java.util.List;

public record GetQuestionGroupRootTopicResponse(
        @JsonProperty("children")
        List<QuestionGroupTopicSummary> children,
        @JsonProperty("question_groups")
        List<QuestionGroupSummary> questionGroups
) {
}
