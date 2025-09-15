package com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic;

import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.content.quiz.question.QuestionGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question_group_topic")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class QuestionGroupTopic extends BaseEntity {
    @OneToMany(mappedBy = "topic")
    private Set<QuestionGroup> questionGroups = new HashSet<>();
}
