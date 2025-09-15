package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentPositionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentStatusRequest;

import java.util.UUID;

public interface SectionContentManagementService {
    void update(UpdateSectionContentRequest updateSectionContentRequest);

    void updatePosition(UpdateSectionContentPositionRequest updateSectionContentPositionRequest);

    void updateStatus(UpdateSectionContentStatusRequest updateSectionContentStatusRequest);

    void updateSection(UpdateSectionContentSectionRequest updateSectionContentSectionRequest);

    void delete(UUID sectionContentId);
}
