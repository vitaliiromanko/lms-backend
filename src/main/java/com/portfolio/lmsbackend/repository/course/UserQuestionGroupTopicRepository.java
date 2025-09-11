package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.UserQuestionGroupTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserQuestionGroupTopicRepository extends JpaRepository<UserQuestionGroupTopic, UUID> {
    boolean existsByTitle(String title);

    List<UserQuestionGroupTopic> findAllByParentIsNull();
}
