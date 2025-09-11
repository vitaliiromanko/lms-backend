package com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "user_question_group_topic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class UserQuestionGroupTopic extends QuestionGroupTopic {
    @Column(name = "title", nullable = false, unique = true, length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "parent_topic_id")
    private UserQuestionGroupTopic parent;

    @Setter(AccessLevel.NONE)
    @OneToMany(targetEntity = UserQuestionGroupTopic.class,
            mappedBy = "parent", cascade = ALL, orphanRemoval = true)
    private Set<UserQuestionGroupTopic> children = new HashSet<>();

    public UserQuestionGroupTopic(String title, UserQuestionGroupTopic parent) {
        this.title = title;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "title = " + getTitle() + ", " +
                "parent = " + getParent() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
