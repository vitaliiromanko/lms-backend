package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.question.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, UUID> {
    List<QuestionGroup> findAllByTopicIsNull();
}
