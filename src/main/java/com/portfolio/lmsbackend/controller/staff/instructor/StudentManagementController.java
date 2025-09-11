package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.*;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetEnrolledStudentsByCourseResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetNotEnrolledStudentsByCourseResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetQuizGroupAccessRestrictionResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.StudentManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/student/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class StudentManagementController {
    private final StudentManagementService studentManagementService;

    @GetMapping("/course/not-enrolled")
    public ResponseEntity<Page<GetNotEnrolledStudentsByCourseResponse>> getNotEnrolledStudentsByCourse(
            @Valid @RequestBody GetNotEnrolledStudentsByCourseRequest getNotEnrolledStudentsByCourseRequest
    ) {
        return ResponseEntity.ok().body(studentManagementService
                .getNotEnrolledStudentsByCourse(getNotEnrolledStudentsByCourseRequest));
    }

    @GetMapping("/course/enrolled")
    public ResponseEntity<Page<GetEnrolledStudentsByCourseResponse>> getEnrolledStudentsByCourse(
            @Valid @RequestBody GetEnrolledStudentsByCourseRequest getEnrolledStudentsByCourseRequest
    ) {
        return ResponseEntity.ok().body(studentManagementService
                .getEnrolledStudentsByCourse(getEnrolledStudentsByCourseRequest));
    }

    @PutMapping("/course/enroll")
    public ResponseEntity<String> enrollStudentsInCourse(
            @Valid @RequestBody EnrollStudentsInCourseRequest enrollStudentsInCourseRequest
    ) {
        studentManagementService.enrollStudentsInCourse(enrollStudentsInCourseRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/course/exclude")
    public ResponseEntity<String> excludeStudentsFromCourse(
            @Valid @RequestBody ExcludeStudentsFromCourseRequest excludeStudentsFromCourseRequest
    ) {
        studentManagementService.excludeStudentsFromCourse(excludeStudentsFromCourseRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @GetMapping("/quiz/group/access-restriction")
    public ResponseEntity<Page<GetQuizGroupAccessRestrictionResponse>> getQuizGroupAccessRestrictionsByGroup(
            @Valid @RequestBody GetQuizGroupAccessRestrictionsRequest getQuizGroupAccessRestrictionsRequest
    ) {
        return ResponseEntity.ok().body(studentManagementService
                .getQuizGroupAccessRestrictionsByGroup(getQuizGroupAccessRestrictionsRequest));
    }

    @PostMapping("/quiz/group/access-restriction")
    public ResponseEntity<String> createQuizGroupAccessRestrictions(
            @Valid @RequestBody CreateQuizGroupAccessRestrictionsRequest createQuizGroupAccessRestrictionsRequest
    ) {
        studentManagementService.createQuizGroupAccessRestrictions(createQuizGroupAccessRestrictionsRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/quiz/group/access-restriction")
    public ResponseEntity<String> deleteQuizGroupAccessRestrictions(
            @Valid @RequestBody DeleteQuizGroupAccessRestrictionsRequest deleteQuizGroupAccessRestrictionsRequest
    ) {
        studentManagementService.deleteQuizGroupAccessRestrictions(deleteQuizGroupAccessRestrictionsRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
