package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.course.response.GetCourseResponse;

import java.util.UUID;

public interface CourseService {
    GetCourseResponse getCourse(UUID userId, UUID courseId);
}
