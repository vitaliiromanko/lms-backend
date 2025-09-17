package com.portfolio.lmsbackend.security.preauthorize;

import com.portfolio.lmsbackend.repository.course.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.portfolio.lmsbackend.enums.course.SectionStatus.VISIBLE;
import static com.portfolio.lmsbackend.security.preauthorize.SecurityHelper.isStudent;

@Component
@RequiredArgsConstructor
public class SectionSecurity {
    private final SectionRepository sectionRepository;
    private final CourseSecurity courseSecurity;

    public boolean canAccess(Authentication authentication, UUID sectionId) {
        return !isStudent(authentication) || canStudentAccess(authentication, sectionId);
    }

    protected boolean canStudentAccess(Authentication authentication, UUID sectionId) {
        Optional<UUID> courseIdOpt = sectionRepository.findCourseIdBySectionId(sectionId);
        if (courseIdOpt.isEmpty()) {
            return false;
        }

        if (!courseSecurity.canStudentAccess(authentication, courseIdOpt.get())) {
            return false;
        }

        return isVisible(sectionId);
    }

    private boolean isVisible(UUID sectionId) {
        return sectionRepository.existsByIdAndStatus(sectionId, VISIBLE);
    }
}
