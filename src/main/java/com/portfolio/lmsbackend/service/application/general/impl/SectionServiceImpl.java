package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.section.response.GetSectionResponse;
import com.portfolio.lmsbackend.model.course.Section;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.service.application.general.SectionService;
import com.portfolio.lmsbackend.service.application.helper.SectionServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.portfolio.lmsbackend.enums.content.SectionContentStatus.VISIBLE;
import static com.portfolio.lmsbackend.service.application.helper.UserServiceHelper.unexpectedUserType;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final UserServiceHelper userServiceHelper;
    private final SectionServiceHelper sectionServiceHelper;

    @Override
    @Transactional
    public GetSectionResponse getSection(UUID userId, UUID sectionId) {
        User user = userServiceHelper.findByIdOrThrow(userId);
        Section section = sectionServiceHelper.findByIdOrThrow(sectionId);
        return new GetSectionResponse(
                section,
                switch (user) {
                    case Staff ignored -> section.getContents();
                    case Student ignored -> section.getContents().stream()
                            .filter(s -> s.getStatus() == VISIBLE)
                            .toList();
                    default -> throw unexpectedUserType(user);
                }
        );
    }
}
