package com.portfolio.lmsbackend.security.preauthorize;

import com.portfolio.lmsbackend.repository.course.CourseRepository;
import com.portfolio.lmsbackend.repository.course.CourseStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.portfolio.lmsbackend.enums.course.CourseStatus.VISIBLE;
import static com.portfolio.lmsbackend.security.preauthorize.SecurityHelper.isStudent;

@Component
@RequiredArgsConstructor
public class CourseSecurity {
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;

    public boolean canAccess(Authentication authentication, UUID courseId) {
        return !isStudent(authentication) || canStudentAccess(authentication, courseId);
    }

    protected boolean canStudentAccess(Authentication authentication, UUID courseId) {
        if (!isVisible(courseId)) {
            return false;
        }

        return isEnrolled(courseId, UUID.fromString(authentication.getName()));
    }

    private boolean isVisible(UUID courseId) {
        return courseRepository.existsByIdAndStatus(courseId, VISIBLE);
    }

    private boolean isEnrolled(UUID courseId, UUID studentId) {
        return courseStudentRepository.existsByCourseIdAndStudentId(courseId, studentId);
    }
}
