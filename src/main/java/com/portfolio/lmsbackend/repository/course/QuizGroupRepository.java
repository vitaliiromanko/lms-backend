package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizGroupRepository extends JpaRepository<QuizGroup, UUID> {
}
