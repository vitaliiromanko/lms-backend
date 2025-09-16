package com.portfolio.lmsbackend.security.preauthorize;

import com.portfolio.lmsbackend.repository.course.CourseRepository;
import com.portfolio.lmsbackend.repository.course.CourseStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.portfolio.lmsbackend.enums.course.CourseStatus.VISIBLE;

@Component
@RequiredArgsConstructor
public class CourseSecurity {
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;

    public boolean canAccess(Authentication authentication, UUID courseId) {
        if (!isStudent(authentication)) {
            return true;
        }

        if (!isVisible(courseId)) {
            return false;
        }

        return isEnrolled(courseId, UUID.fromString(authentication.getName()));
    }

    public boolean isStudent(Authentication authentication) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return roles.contains("ROLE_STUDENT");
    }

    private boolean isVisible(UUID courseId) {
        return courseRepository.existsByIdAndStatus(courseId, VISIBLE);
    }

    private boolean isEnrolled(UUID courseId, UUID studentId) {
        return courseStudentRepository.existsByCourseIdAndStudentId(courseId, studentId);
    }
}
