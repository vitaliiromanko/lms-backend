package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.Quiz;
import com.portfolio.lmsbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, UUID> {
    List<Attempt> findByQuizInAndUser(Collection<Quiz> quizzes, User user);

    void deleteByQuizInAndUserIn(Collection<Quiz> quiz, Collection<User> users);
}
