package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.CourseNotFoundException;
import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.repository.course.CourseRepository;
import org.springframework.stereotype.Component;

@Component
public class CourseServiceHelper extends BaseServiceHelper<Course, CourseRepository, CourseNotFoundException> {
    private final SectionServiceHelper sectionServiceHelper;

    protected CourseServiceHelper(CourseRepository repository, SectionServiceHelper sectionServiceHelper) {
        super(repository, CourseNotFoundException::new);
        this.sectionServiceHelper = sectionServiceHelper;
    }

    public void delete(Course course) {
        course.getSections().forEach(sectionServiceHelper::delete);
        repository.delete(course);
    }
}
