package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.ArchiveQuestionGroupTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArchiveQuestionGroupTopicRepository extends JpaRepository<ArchiveQuestionGroupTopic, UUID> {
    Optional<ArchiveQuestionGroupTopic> findFirstBy();
}
