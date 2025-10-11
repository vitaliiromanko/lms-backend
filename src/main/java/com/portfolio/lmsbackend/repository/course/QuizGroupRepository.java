package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizGroupRepository extends JpaRepository<QuizGroup, UUID> {
    @Query("SELECT qg.maxAttempts FROM QuizGroup qg WHERE qg.id = :groupId")
    Integer findMaxAttemptsById(@Param("groupId") UUID groupId);
}
