package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus;
import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.QuestionGroupTopic;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus.DRAFT;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "question_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class QuestionGroup extends BaseEntity {
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(value = STRING)
    @Column(name = "status", nullable = false)
    private QuestionGroupStatus status = DRAFT;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private QuestionGroupTopic topic;

    @OneToMany(mappedBy = "group", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private List<Question> questions = new ArrayList<>();

    public QuestionGroup(String title, QuestionGroupTopic topic) {
        this.title = title;
        this.topic = topic;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "title = " + getTitle() + ", " +
                "status = " + getStatus() + ", " +
                "topic = " + getTopic() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
