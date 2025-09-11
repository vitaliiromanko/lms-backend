package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    boolean existsByTitle(String title);
}
