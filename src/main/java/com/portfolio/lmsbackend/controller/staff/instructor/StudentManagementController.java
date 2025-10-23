package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.*;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetEnrolledStudentsByCourseResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetNotEnrolledStudentsByCourseResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.GetQuizGroupAccessRestrictionResponse;
import com.portfolio.lmsbackend.service.application.staff.instructor.StudentManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(
        name = "Instructor / StudentManagementController",
        description = "Endpoints for managing students"
)
@RestController
@RequestMapping("/student/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class StudentManagementController {
    private final StudentManagementService studentManagementService;

    @Operation(
            summary = "Get not-enrolled student summaries",
            description = "Endpoint to retrieve a page of brief information about students who are not enrolled in a course."
    )
    @GetMapping("/course/not-enrolled")
    public ResponseEntity<Page<GetNotEnrolledStudentsByCourseResponse>> getNotEnrolledStudentsByCourse(
            @Valid @RequestBody GetNotEnrolledStudentsByCourseRequest getNotEnrolledStudentsByCourseRequest
    ) {
        return ResponseEntity.ok().body(studentManagementService
                .getNotEnrolledStudentsByCourse(getNotEnrolledStudentsByCourseRequest));
    }

    @Operation(
            summary = "Get enrolled student summaries",
            description = "Endpoint to retrieve a page of brief information about students who are enrolled in a course."
    )
    @GetMapping("/course/enrolled")
    public ResponseEntity<Page<GetEnrolledStudentsByCourseResponse>> getEnrolledStudentsByCourse(
            @Valid @RequestBody GetEnrolledStudentsByCourseRequest getEnrolledStudentsByCourseRequest
    ) {
        return ResponseEntity.ok().body(studentManagementService
                .getEnrolledStudentsByCourse(getEnrolledStudentsByCourseRequest));
    }

    @Operation(
            summary = "Enroll students in course",
            description = "Endpoint to enroll students in a course."
    )
    @PutMapping("/course/enroll")
    public ResponseEntity<String> enrollStudentsInCourse(
            @Valid @RequestBody EnrollStudentsInCourseRequest enrollStudentsInCourseRequest
    ) {
        studentManagementService.enrollStudentsInCourse(enrollStudentsInCourseRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Exclude students from course",
            description = "Endpoint to exclude students from a course."
    )
    @DeleteMapping("/course/exclude")
    public ResponseEntity<String> excludeStudentsFromCourse(
            @Valid @RequestBody ExcludeStudentsFromCourseRequest excludeStudentsFromCourseRequest
    ) {
        studentManagementService.excludeStudentsFromCourse(excludeStudentsFromCourseRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Get quiz group access restrictions",
            description = "Endpoint to retrieve a page of information about students' access restrictions to a quiz group."
    )
    @GetMapping("/quiz/group/access-restriction")
    public ResponseEntity<Page<GetQuizGroupAccessRestrictionResponse>> getQuizGroupAccessRestrictionsByGroup(
            @Valid @RequestBody GetQuizGroupAccessRestrictionsRequest getQuizGroupAccessRestrictionsRequest
    ) {
        return ResponseEntity.ok().body(studentManagementService
                .getQuizGroupAccessRestrictionsByGroup(getQuizGroupAccessRestrictionsRequest));
    }

    @Operation(
            summary = "Create quiz group access restrictions",
            description = "Endpoint to create new quiz group access restrictions."
    )
    @PostMapping("/quiz/group/access-restriction")
    public ResponseEntity<String> createQuizGroupAccessRestrictions(
            @Valid @RequestBody CreateQuizGroupAccessRestrictionsRequest createQuizGroupAccessRestrictionsRequest
    ) {
        studentManagementService.createQuizGroupAccessRestrictions(createQuizGroupAccessRestrictionsRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Delete quiz group access restrictions",
            description = "Endpoint to delete quiz group access restrictions."
    )
    @DeleteMapping("/quiz/group/access-restriction")
    public ResponseEntity<String> deleteQuizGroupAccessRestrictions(
            @Valid @RequestBody DeleteQuizGroupAccessRestrictionsRequest deleteQuizGroupAccessRestrictionsRequest
    ) {
        studentManagementService.deleteQuizGroupAccessRestrictions(deleteQuizGroupAccessRestrictionsRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
