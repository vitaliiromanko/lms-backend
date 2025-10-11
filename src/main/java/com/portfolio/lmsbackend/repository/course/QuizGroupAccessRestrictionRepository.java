package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestrictionId;
import com.portfolio.lmsbackend.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizGroupAccessRestrictionRepository
        extends JpaRepository<QuizGroupAccessRestriction, QuizGroupAccessRestrictionId> {
    Optional<QuizGroupAccessRestriction> findByGroupAndStudent(QuizGroup group, Student student);

    Optional<QuizGroupAccessRestriction> findByGroupIdAndStudentId(UUID groupId, UUID studentId);

    void deleteByGroupAndStudentIn(QuizGroup group, Collection<Student> students);

    void deleteByGroupIdAndStudentIdIn(UUID groupId, Collection<UUID> studentIds);

    void deleteByGroupInAndStudentIn(Collection<QuizGroup> groups, Collection<Student> students);
}
