package com.portfolio.lmsbackend.service.application.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.CreateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.course.request.UpdateAdminCourseStatusRequest;

public interface CourseManagementService {
    void create(String userId, CreateAdminCourseRequest createAdminCourseRequest);

    void update(UpdateAdminCourseRequest updateAdminCourseRequest);

    void updateStatus(UpdateAdminCourseStatusRequest updateAdminCourseStatusRequest);

    void delete(String courseId);
}
