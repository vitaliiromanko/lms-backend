package com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.ChoiceQuestionDetailed;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.FillTheGapsQuestionDetailed;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.QuestionDetailed;
import com.portfolio.lmsbackend.dto.staff.instructor.management.question.response.TextLongQuestionDetailed;
import com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus;
import com.portfolio.lmsbackend.model.content.quiz.question.QuestionGroup;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.portfolio.lmsbackend.service.application.helper.QuestionServiceHelper.mapQuestionTo;

public class GetQuestionGroupResponse extends QuestionGroupSummary {
    @JsonView(Views.General.class)
    private final QuestionGroupStatus status;

    @JsonView(Views.General.class)
    private final QuestionDetailed actualQuestion;

    @JsonView(Views.General.class)
    private final List<QuestionDetailed> historyChanges;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime createdAt;

    @JsonView(Views.Detailed.class)
    private final LocalDateTime updatedAt;

    public GetQuestionGroupResponse(QuestionGroup group) {
        super(group);
        this.status = group.getStatus();

        List<QuestionDetailed> questionDetailedList = group.getQuestions().stream()
                .map(q -> mapQuestionTo(q,
                        ChoiceQuestionDetailed::new,
                        ChoiceQuestionDetailed::new,
                        FillTheGapsQuestionDetailed::new,
                        TextLongQuestionDetailed::new))
                .toList();
        this.actualQuestion = questionDetailedList.getLast();
        this.historyChanges = questionDetailedList.size() > 1
                ? questionDetailedList.subList(0, questionDetailedList.size() - 1)
                : Collections.emptyList();

        this.createdAt = group.getCreatedAt();
        this.updatedAt = group.getUpdatedAt();
    }

    @JsonCreator
    protected GetQuestionGroupResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("status") QuestionGroupStatus status,
            @JsonProperty("actual_question") QuestionDetailed actualQuestion,
            @JsonProperty("history_changes") List<QuestionDetailed> historyChanges,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("updated_at") LocalDateTime updatedAt
    ) {
        super(id, title);
        this.status = status;
        this.actualQuestion = actualQuestion;
        this.historyChanges = historyChanges;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonProperty("status")
    public QuestionGroupStatus status() {
        return status;
    }

    @JsonProperty("actual_question")
    public QuestionDetailed actualQuestion() {
        return actualQuestion;
    }

    @JsonProperty("history_changes")
    public List<QuestionDetailed> historyChanges() {
        return historyChanges;
    }

    @JsonProperty("created_at")
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @JsonProperty("updated_at")
    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
