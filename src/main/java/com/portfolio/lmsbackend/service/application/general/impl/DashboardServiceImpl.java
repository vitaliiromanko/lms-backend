package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.dashboard.response.GetDashboardResponse;
import com.portfolio.lmsbackend.dto.general.dashboard.response.GetStaffDashboardResponse;
import com.portfolio.lmsbackend.dto.general.dashboard.response.GetStudentDashboardResponse;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.course.CourseRepository;
import com.portfolio.lmsbackend.service.application.general.DashboardService;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.portfolio.lmsbackend.service.application.helper.UserServiceHelper.unexpectedUserType;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final CourseRepository courseRepository;
    private final UserServiceHelper userServiceHelper;

    @Override
    @Transactional
    public GetDashboardResponse getDashboard(UUID userId) {
        User user = userServiceHelper.findByIdOrThrow(userId);
        return switch (user) {
            case Staff ignored -> GetStaffDashboardResponse.fromCourses(courseRepository.findAll());
            case Student student -> new GetStudentDashboardResponse(student);
            default -> throw unexpectedUserType(user);
        };
    }
}
