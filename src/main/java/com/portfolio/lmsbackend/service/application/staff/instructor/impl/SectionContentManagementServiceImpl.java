package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentPositionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentStatusRequest;
import com.portfolio.lmsbackend.exception.InvalidPositionException;
import com.portfolio.lmsbackend.exception.course.SectionContentMustBelongToSameCourseException;
import com.portfolio.lmsbackend.model.content.SectionContent;
import com.portfolio.lmsbackend.model.course.Section;
import com.portfolio.lmsbackend.repository.course.SectionContentRepository;
import com.portfolio.lmsbackend.repository.course.SectionRepository;
import com.portfolio.lmsbackend.service.application.helper.SectionContentServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.SectionServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.SectionContentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionContentManagementServiceImpl implements SectionContentManagementService {
    private final SectionContentRepository sectionContentRepository;
    private final SectionRepository sectionRepository;
    private final SectionContentServiceHelper sectionContentServiceHelper;
    private final SectionServiceHelper sectionServiceHelper;

    @Override
    @Transactional
    public void update(UpdateSectionContentRequest updateSectionContentRequest) {
        SectionContent sectionContent = sectionContentServiceHelper
                .findByIdOrThrow(updateSectionContentRequest.sectionContentId());
        sectionContent.setTitle(updateSectionContentRequest.title());
        sectionContentRepository.save(sectionContent);
    }

    @Override
    @Transactional
    public void updatePosition(UpdateSectionContentPositionRequest updateSectionContentPositionRequest) {
        SectionContent sectionContent = sectionContentServiceHelper
                .findByIdOrThrow(updateSectionContentPositionRequest.sectionContentId());
        Section section = sectionContent.getSection();
        List<SectionContent> contents = section.getContents();

        if (updateSectionContentPositionRequest.newPosition() > contents.size() - 1) {
            throw new InvalidPositionException();
        }

        contents.remove(sectionContent);
        contents.add(updateSectionContentPositionRequest.newPosition(), sectionContent);

        sectionRepository.save(section);
    }

    @Override
    @Transactional
    public void updateStatus(UpdateSectionContentStatusRequest updateSectionContentStatusRequest) {
        SectionContent sectionContent = sectionContentServiceHelper
                .findByIdOrThrow(updateSectionContentStatusRequest.sectionContentId());
        sectionContent.setStatus(updateSectionContentStatusRequest.newStatus());
        sectionContentRepository.save(sectionContent);
    }

    @Override
    @Transactional
    public void updateSection(UpdateSectionContentSectionRequest updateSectionContentSectionRequest) {
        SectionContent sectionContent = sectionContentServiceHelper
                .findByIdOrThrow(updateSectionContentSectionRequest.sectionContentId());
        Section oldSection = sectionContent.getSection();
        Section newSection = sectionServiceHelper.findByIdOrThrow(updateSectionContentSectionRequest.newSectionId());

        if (!oldSection.equals(newSection)) {
            if (!oldSection.getCourse().equals(newSection.getCourse())) {
                throw new SectionContentMustBelongToSameCourseException();
            }

            oldSection.getContents().remove(sectionContent);
            sectionContent.setSection(newSection);
            newSection.getContents().add(sectionContent);
        }

        sectionRepository.save(oldSection);
        sectionRepository.save(newSection);
    }

    @Override
    @Transactional
    public void delete(String sectionContentId) {
        sectionContentServiceHelper.delete(sectionContentServiceHelper.findByIdOrThrow(sectionContentId));
    }
}
