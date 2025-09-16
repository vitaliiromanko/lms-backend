package com.portfolio.lmsbackend.dto.general.dashboard.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.general.course.response.CourseStaffDashboardSummary;
import com.portfolio.lmsbackend.model.course.Course;

import java.util.Comparator;
import java.util.List;

public class GetStaffDashboardResponse extends GetDashboardResponse {
    @JsonView(Views.General.class)
    private final List<CourseStaffDashboardSummary> courses;

    public static GetStaffDashboardResponse fromCourses(List<Course> courses) {
        return new GetStaffDashboardResponse(
                courses.stream()
                        .map(CourseStaffDashboardSummary::new)
                        .sorted(Comparator.comparing(CourseStaffDashboardSummary::title))
                        .toList()
        );
    }

    @JsonCreator
    protected GetStaffDashboardResponse(
            @JsonProperty("courses") List<CourseStaffDashboardSummary> courses
    ) {
        this.courses = courses;
    }

    @JsonProperty("courses")
    public List<CourseStaffDashboardSummary> courses() {
        return courses;
    }
}
