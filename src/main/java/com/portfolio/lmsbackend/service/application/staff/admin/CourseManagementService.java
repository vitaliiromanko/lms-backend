package com.portfolio.lmsbackend.service.application.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.CreateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseStatusRequest;

import java.util.UUID;

public interface CourseManagementService {
    void create(UUID userId, CreateAdminCourseRequest createAdminCourseRequest);

    void update(UpdateAdminCourseRequest updateAdminCourseRequest);

    void updateStatus(UpdateAdminCourseStatusRequest updateAdminCourseStatusRequest);

    void delete(UUID courseId);
}
