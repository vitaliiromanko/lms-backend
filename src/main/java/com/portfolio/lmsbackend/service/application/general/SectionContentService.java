package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.sectioncontent.response.GetSectionContentResponse;

import java.util.UUID;

public interface SectionContentService {
    GetSectionContentResponse getSectionContent(UUID userId, UUID sectionContentId);
}
