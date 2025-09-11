package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.SectionNotFoundException;
import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.model.course.Section;
import com.portfolio.lmsbackend.repository.course.CourseRepository;
import com.portfolio.lmsbackend.repository.course.SectionRepository;
import org.springframework.stereotype.Component;

@Component
public class SectionServiceHelper extends BaseServiceHelper<Section, SectionRepository, SectionNotFoundException> {
    private final CourseRepository courseRepository;
    private final SectionContentServiceHelper sectionContentServiceHelper;

    protected SectionServiceHelper(SectionRepository repository, CourseRepository courseRepository,
                                   SectionContentServiceHelper sectionContentServiceHelper) {
        super(repository, SectionNotFoundException::new);
        this.courseRepository = courseRepository;
        this.sectionContentServiceHelper = sectionContentServiceHelper;
    }

    public void delete(Section section) {
        Course course = section.getCourse();

        section.getContents().forEach(sectionContentServiceHelper::delete);

        course.getSections().remove(section);
        courseRepository.save(course);
    }
}
