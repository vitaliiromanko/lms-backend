package com.portfolio.lmsbackend.dto.staff.admin.management.category.response;

import com.portfolio.lmsbackend.dto.staff.admin.management.course.response.AdminCourseSummary;
import com.portfolio.lmsbackend.model.course.Course;

public class AdminCategoryCourseSummary extends AdminCourseSummary {
    public AdminCategoryCourseSummary(Course course) {
        super(course);
    }
}
