package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.course.coursestudent.CourseStudent;
import com.portfolio.lmsbackend.model.course.coursestudent.CourseStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentId> {
    boolean existsByCourseIdAndStudentId(UUID courseId, UUID studentId);
}
