package com.portfolio.lmsbackend.service.application.staff.admin.impl;

import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.CreateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseStatusRequest;
import com.portfolio.lmsbackend.exception.course.CourseAlreadyExistsException;
import com.portfolio.lmsbackend.model.course.Category;
import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.repository.course.CourseRepository;
import com.portfolio.lmsbackend.service.application.helper.CategoryServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.CourseServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.admin.CourseManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseManagementServiceImpl implements CourseManagementService {
    private final CourseRepository courseRepository;
    private final CourseServiceHelper courseServiceHelper;
    private final CategoryServiceHelper categoryServiceHelper;
    private final UserServiceHelper userServiceHelper;

    @Override
    public void create(UUID userId, CreateAdminCourseRequest createAdminCourseRequest) {
        if (courseRepository.existsByTitle(createAdminCourseRequest.title())) {
            throw new CourseAlreadyExistsException();
        }

        courseRepository.save(new Course(
                createAdminCourseRequest.title(),
                categoryServiceHelper.findByIdOrThrow(createAdminCourseRequest.categoryId()),
                userServiceHelper.findByIdAndTypeOrThrow(userId, Staff.class)
        ));
    }

    @Override
    @Transactional
    public void update(UpdateAdminCourseRequest updateAdminCourseRequest) {
        Course course = courseServiceHelper.findByIdOrThrow(updateAdminCourseRequest.courseId());
        Category newCategory = categoryServiceHelper.findByIdOrThrow(updateAdminCourseRequest.categoryId());

        course.setCategory(newCategory);

        if (!course.getTitle().equals(updateAdminCourseRequest.title())) {
            if (courseRepository.existsByTitle(updateAdminCourseRequest.title())) {
                throw new CourseAlreadyExistsException();
            }

            course.setTitle(updateAdminCourseRequest.title());
        }

        courseRepository.save(course);
    }

    @Override
    public void updateStatus(UpdateAdminCourseStatusRequest updateAdminCourseStatusRequest) {
        Course course = courseServiceHelper.findByIdOrThrow(updateAdminCourseStatusRequest.courseId());
        course.setStatus(updateAdminCourseStatusRequest.newStatus());
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(UUID courseId) {
        courseServiceHelper.delete(courseServiceHelper.findByIdOrThrow(courseId));
    }
}
