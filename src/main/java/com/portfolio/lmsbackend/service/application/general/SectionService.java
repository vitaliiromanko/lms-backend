package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.section.response.GetSectionResponse;

import java.util.UUID;

public interface SectionService {
    GetSectionResponse getSection(UUID userId, UUID sectionId);
}
