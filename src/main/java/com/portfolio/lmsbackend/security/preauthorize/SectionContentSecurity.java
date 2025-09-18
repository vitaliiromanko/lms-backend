package com.portfolio.lmsbackend.security.preauthorize;

import com.portfolio.lmsbackend.repository.course.SectionContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.SectionContentStatus.VISIBLE;
import static com.portfolio.lmsbackend.security.preauthorize.SecurityHelper.isStudent;

@Component
@RequiredArgsConstructor
public class SectionContentSecurity {
    private final SectionContentRepository sectionContentRepository;
    private final SectionSecurity sectionSecurity;

    public boolean canAccess(Authentication authentication, UUID sectionContentId) {
        return !isStudent(authentication) || canStudentAccess(authentication, sectionContentId);
    }

    protected boolean canStudentAccess(Authentication authentication, UUID sectionContentId) {
        Optional<UUID> sectionIdOpt = sectionContentRepository.findSectionIdBySectionContentId(sectionContentId);
        if (sectionIdOpt.isEmpty()) {
            return false;
        }

        if (!sectionSecurity.canStudentAccess(authentication, sectionIdOpt.get())) {
            return false;
        }

        return isVisible(sectionContentId);
    }

    private boolean isVisible(UUID sectionContentId) {
        return sectionContentRepository.existsByIdAndStatus(sectionContentId, VISIBLE);
    }
}
