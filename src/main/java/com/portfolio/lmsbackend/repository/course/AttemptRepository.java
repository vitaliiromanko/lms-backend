package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.Quiz;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, UUID> {
    List<Attempt> findByQuizInAndUserOrderByCreatedAtAsc(Collection<Quiz> quizzes, User user);

    @Query("""
                SELECT COUNT(a)
                FROM Attempt a
                WHERE a.user = :user
                  AND a.quiz.group = :group
                  AND a.createdAt <= :createdAt
            """)
    long countAttemptsUpTo(
            @Param("user") User user,
            @Param("group") QuizGroup group,
            @Param("createdAt") LocalDateTime createdAt
    );

    @Query("""
                SELECT a.quiz.group.id
                FROM Attempt a
                WHERE a.id = :attemptId
            """)
    Optional<UUID> findQuizGroupIdByAttemptId(@Param("attemptId") UUID attemptId);

    @Query("""
                SELECT COUNT(a)
                FROM Attempt a
                WHERE a.quiz.group.id = :groupId
                  AND a.user.id = :userId
            """)
    long countByQuizGroupIdAndUserId(@Param("groupId") UUID groupId, @Param("userId") UUID userId);

    boolean existsByIdAndUserId(UUID id, UUID userId);

    boolean existsByIdAndUserIdAndStatus(UUID id, UUID userId, AttemptStatus status);

    boolean existsByIdAndUserIdAndStatusNot(UUID id, UUID userId, AttemptStatus status);

    void deleteByQuizInAndUserIn(Collection<Quiz> quiz, Collection<User> users);
}
