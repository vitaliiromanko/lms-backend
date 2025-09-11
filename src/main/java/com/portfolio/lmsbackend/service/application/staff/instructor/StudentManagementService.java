package com.portfolio.lmsbackend.service.application.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.*;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetEnrolledStudentsByCourseResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetNotEnrolledStudentsByCourseResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetQuizGroupAccessRestrictionResponse;
import org.springframework.data.domain.Page;

public interface StudentManagementService {
    Page<GetNotEnrolledStudentsByCourseResponse> getNotEnrolledStudentsByCourse(GetNotEnrolledStudentsByCourseRequest getNotEnrolledStudentsByCourseRequest);

    Page<GetEnrolledStudentsByCourseResponse> getEnrolledStudentsByCourse(GetEnrolledStudentsByCourseRequest getEnrolledStudentsByCourseRequest);

    void enrollStudentsInCourse(EnrollStudentsInCourseRequest enrollStudentsInCourseRequest);

    void excludeStudentsFromCourse(ExcludeStudentsFromCourseRequest excludeStudentsFromCourseRequest);

    Page<GetQuizGroupAccessRestrictionResponse> getQuizGroupAccessRestrictionsByGroup(GetQuizGroupAccessRestrictionsRequest getQuizGroupAccessRestrictionsRequest);

    void createQuizGroupAccessRestrictions(CreateQuizGroupAccessRestrictionsRequest createQuizGroupAccessRestrictionsRequest);

    void deleteQuizGroupAccessRestrictions(DeleteQuizGroupAccessRestrictionsRequest deleteQuizGroupAccessRestrictionsRequest);
}
