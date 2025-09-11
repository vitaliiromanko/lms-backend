package com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "archive_question_group_topic")
public class ArchiveQuestionGroupTopic extends QuestionGroupTopic {
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
