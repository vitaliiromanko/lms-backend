package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.CreateSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionPositionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionStatusRequest;

import java.util.UUID;

public interface SectionManagementService {
    void create(CreateSectionRequest createSectionRequest);

    void update(UpdateSectionRequest updateSectionRequest);

    void updatePosition(UpdateSectionPositionRequest updateSectionPositionRequest);

    void updateStatus(UpdateSectionStatusRequest updateSectionStatusRequest);

    void delete(UUID sectionId);
}
