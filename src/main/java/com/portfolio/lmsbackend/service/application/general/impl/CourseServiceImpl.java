package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.course.response.GetCourseResponse;
import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.service.application.general.CourseService;
import com.portfolio.lmsbackend.service.application.helper.CourseServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.portfolio.lmsbackend.service.application.helper.UserServiceHelper.unexpectedUserType;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final UserServiceHelper userServiceHelper;
    private final CourseServiceHelper courseServiceHelper;

    @Override
    @Transactional
    public GetCourseResponse getCourse(UUID userId, UUID courseId) {
        User user = userServiceHelper.findByIdOrThrow(userId);
        Course course = courseServiceHelper.findByIdOrThrow(courseId);
        return new GetCourseResponse(
                course,
                switch (user) {
                    case Staff ignored -> true;
                    case Student ignored -> false;
                    default -> throw unexpectedUserType(user);
                }
        );
    }
}
