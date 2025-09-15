package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.CreateSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionPositionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionStatusRequest;
import com.portfolio.lmsbackend.exception.InvalidPositionException;
import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.model.course.Section;
import com.portfolio.lmsbackend.repository.course.CourseRepository;
import com.portfolio.lmsbackend.repository.course.SectionRepository;
import com.portfolio.lmsbackend.service.application.helper.CourseServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.SectionServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.SectionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectionManagementServiceImpl implements SectionManagementService {
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final SectionServiceHelper sectionServiceHelper;
    private final CourseServiceHelper courseServiceHelper;

    @Override
    @Transactional
    public void create(CreateSectionRequest createSectionRequest) {
        Course course = courseServiceHelper.findByIdOrThrow(createSectionRequest.courseId());
        course.getSections().add(new Section(createSectionRequest.title(), course));
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void update(UpdateSectionRequest updateSectionRequest) {
        Section section = sectionServiceHelper.findByIdOrThrow(updateSectionRequest.sectionId());
        section.setTitle(updateSectionRequest.title());
        sectionRepository.save(section);
    }

    @Override
    @Transactional
    public void updatePosition(UpdateSectionPositionRequest updateSectionPositionRequest) {
        Section section = sectionServiceHelper.findByIdOrThrow(updateSectionPositionRequest.sectionId());
        Course course = section.getCourse();
        List<Section> sections = course.getSections();

        if (updateSectionPositionRequest.newPosition() > sections.size() - 1) {
            throw new InvalidPositionException();
        }

        sections.remove(section);
        sections.add(updateSectionPositionRequest.newPosition(), section);

        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void updateStatus(UpdateSectionStatusRequest updateSectionStatusRequest) {
        Section section = sectionServiceHelper.findByIdOrThrow(updateSectionStatusRequest.sectionId());
        section.setStatus(updateSectionStatusRequest.newStatus());
        sectionRepository.save(section);
    }

    @Override
    @Transactional
    public void delete(UUID sectionId) {
        sectionServiceHelper.delete(sectionServiceHelper.findByIdOrThrow(sectionId));
    }
}
