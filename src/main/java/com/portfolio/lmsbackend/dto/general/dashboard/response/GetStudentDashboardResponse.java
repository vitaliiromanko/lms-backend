package com.portfolio.lmsbackend.dto.general.dashboard.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.general.course.response.CourseStudentDashboardSummary;
import com.portfolio.lmsbackend.model.user.Student;

import java.util.Comparator;
import java.util.List;

public class GetStudentDashboardResponse extends GetDashboardResponse {
    @JsonView(Views.Basic.class)
    private final List<CourseStudentDashboardSummary> enrolledCourses;

    public GetStudentDashboardResponse(Student student) {
        this(
                student.getCourseStudents().stream()
                        .map(cs -> new CourseStudentDashboardSummary(cs.getCourse()))
                        .sorted(Comparator.comparing(CourseStudentDashboardSummary::title))
                        .toList()
        );
    }

    @JsonCreator
    protected GetStudentDashboardResponse(
            @JsonProperty("enrolled_courses") List<CourseStudentDashboardSummary> enrolledCourses
    ) {
        this.enrolledCourses = enrolledCourses;
    }

    @JsonProperty("enrolled_courses")
    public List<CourseStudentDashboardSummary> enrolledCourses() {
        return enrolledCourses;
    }
}
