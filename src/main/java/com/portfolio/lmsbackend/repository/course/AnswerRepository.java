package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    @Query("""
                SELECT a.attempt.id
                FROM Answer a
                WHERE a.id = :answerId
            """)
    Optional<UUID> findAttemptIdByAnswerId(@Param("answerId") UUID answerId);
}
