package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.*;
import com.portfolio.lmsbackend.dto.staff.instructor.management.student.response.*;
import com.portfolio.lmsbackend.exception.course.StudentsAlreadyEnrolledException;
import com.portfolio.lmsbackend.exception.course.StudentsNotEnrolledException;
import com.portfolio.lmsbackend.model.content.quiz.Quiz;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupBlockedAccessRestriction;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupTimeWindowAccessRestriction;
import com.portfolio.lmsbackend.model.course.Course;
import com.portfolio.lmsbackend.model.course.coursestudent.CourseStudent;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import com.portfolio.lmsbackend.repository.course.CourseRepository;
import com.portfolio.lmsbackend.repository.course.QuizGroupAccessRestrictionCriteriaRepository;
import com.portfolio.lmsbackend.repository.course.QuizGroupAccessRestrictionRepository;
import com.portfolio.lmsbackend.repository.user.EnrolledStudentCriteriaRepository;
import com.portfolio.lmsbackend.repository.user.NotEnrolledStudentCriteriaRepository;
import com.portfolio.lmsbackend.service.application.helper.CourseServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.QuizGroupServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.StudentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.portfolio.lmsbackend.service.application.helper.QuizGroupAccessRestrictionServiceHelper.mapQuizGroupAccessRestrictionTo;

@Service
@RequiredArgsConstructor
public class StudentManagementServiceImpl implements StudentManagementService {
    private final CourseRepository courseRepository;
    private final QuizGroupAccessRestrictionRepository quizGroupAccessRestrictionRepository;
    private final AttemptRepository attemptRepository;
    private final NotEnrolledStudentCriteriaRepository notEnrolledStudentCriteriaRepository;
    private final EnrolledStudentCriteriaRepository enrolledStudentCriteriaRepository;
    private final QuizGroupAccessRestrictionCriteriaRepository quizGroupAccessRestrictionCriteriaRepository;
    private final CourseServiceHelper courseServiceHelper;
    private final QuizGroupServiceHelper quizGroupServiceHelper;
    private final UserServiceHelper userServiceHelper;

    @Override
    public Page<GetNotEnrolledStudentsByCourseResponse> getNotEnrolledStudentsByCourse(
            GetNotEnrolledStudentsByCourseRequest getNotEnrolledStudentsByCourseRequest) {
        return notEnrolledStudentCriteriaRepository.findByCriteria(getNotEnrolledStudentsByCourseRequest)
                .map(GetNotEnrolledStudentsByCourseResponse::new);
    }

    @Override
    public Page<GetEnrolledStudentsByCourseResponse> getEnrolledStudentsByCourse(
            GetEnrolledStudentsByCourseRequest getEnrolledStudentsByCourseRequest) {
        return enrolledStudentCriteriaRepository.findByCriteria(getEnrolledStudentsByCourseRequest)
                .map(GetEnrolledStudentsByCourseResponse::new);
    }

    @Override
    @Transactional
    public void enrollStudentsInCourse(EnrollStudentsInCourseRequest enrollStudentsInCourseRequest) {
        Course course = courseServiceHelper.findByIdOrThrow(enrollStudentsInCourseRequest.courseId());
        Set<Student> enrolledStudents = getEnrolledStudentsInCourse(course);
        Set<Student> studentsToEnroll = getStudentsByIds(enrollStudentsInCourseRequest.studentIds());

        if (!Collections.disjoint(enrolledStudents, studentsToEnroll)) {
            throw new StudentsAlreadyEnrolledException();
        }

        studentsToEnroll.forEach(student -> course.getCourseStudents()
                .add(new CourseStudent(course, student)));

        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void excludeStudentsFromCourse(ExcludeStudentsFromCourseRequest excludeStudentsFromCourseRequest) {
        Course course = courseServiceHelper.findByIdOrThrow(excludeStudentsFromCourseRequest.courseId());
        Set<Student> enrolledStudents = getEnrolledStudentsInCourse(course);
        Set<Student> studentsToExclude = getStudentsByIds(excludeStudentsFromCourseRequest.studentIds());

        if (!enrolledStudents.containsAll(studentsToExclude)) {
            throw new StudentsNotEnrolledException();
        }

        Set<QuizGroup> courseQuizGroups = course.getSections().stream()
                .flatMap(section -> section.getContents().stream()
                        .filter(Objects::nonNull))
                .filter(QuizGroup.class::isInstance)
                .map(QuizGroup.class::cast)
                .collect(Collectors.toSet());

        if (!courseQuizGroups.isEmpty()) {
            quizGroupAccessRestrictionRepository.deleteByGroupInAndStudentIn(courseQuizGroups, studentsToExclude);

            Set<Quiz> courseQuizzes = courseQuizGroups.stream()
                    .flatMap(qg -> qg.getQuizzes().stream())
                    .collect(Collectors.toSet());

            attemptRepository.deleteByQuizInAndUserIn(courseQuizzes, new HashSet<>(studentsToExclude));
        }

        course.getCourseStudents().removeIf(cs -> studentsToExclude.contains(cs.getStudent()));

        courseRepository.save(course);
    }

    @Override
    public Page<GetQuizGroupAccessRestrictionResponse> getQuizGroupAccessRestrictionsByGroup(GetQuizGroupAccessRestrictionsRequest getQuizGroupAccessRestrictionsRequest) {
        return quizGroupAccessRestrictionCriteriaRepository.findByCriteria(getQuizGroupAccessRestrictionsRequest)
                .map(accessRestriction -> mapQuizGroupAccessRestrictionTo(
                        accessRestriction,
                        GetQuizGroupBlockedAccessRestrictionResponse::new,
                        GetQuizGroupTimeWindowAccessRestrictionResponse::new
                ));
    }

    @Override
    @Transactional
    public void createQuizGroupAccessRestrictions(CreateQuizGroupAccessRestrictionsRequest createQuizGroupAccessRestrictionsRequest) {
        QuizGroup group = quizGroupServiceHelper.findByIdOrThrow(createQuizGroupAccessRestrictionsRequest.groupId());
        Set<Student> enrolledStudents = getEnrolledStudentsInCourse(group.getSection().getCourse());
        Set<Student> studentsToRestrict = getStudentsByIds(createQuizGroupAccessRestrictionsRequest.studentIds());

        if (!enrolledStudents.containsAll(studentsToRestrict)) {
            throw new StudentsNotEnrolledException();
        }

        quizGroupAccessRestrictionRepository.deleteByGroupAndStudentIn(group, studentsToRestrict);

        List<QuizGroupAccessRestriction> accessRestrictions = switch (createQuizGroupAccessRestrictionsRequest) {
            case CreateQuizGroupBlockedAccessRestrictionsRequest ignored -> studentsToRestrict.stream()
                    .map(student -> (QuizGroupAccessRestriction) new QuizGroupBlockedAccessRestriction(group, student))
                    .toList();
            case CreateQuizGroupTimeWindowAccessRestrictionsRequest createQuizGroupTimeWindowAccessRestrictionsRequest ->
                    studentsToRestrict.stream()
                            .map(student -> (QuizGroupAccessRestriction) new QuizGroupTimeWindowAccessRestriction(
                                    group,
                                    student,
                                    createQuizGroupTimeWindowAccessRestrictionsRequest.availableFrom(),
                                    createQuizGroupTimeWindowAccessRestrictionsRequest.availableUntil()
                            ))
                            .toList();
            default -> throw new IllegalArgumentException("Invalid CreateQuizGroupAccessRestrictionsRequest");
        };
        quizGroupAccessRestrictionRepository.saveAll(accessRestrictions);
    }

    @Override
    @Transactional
    public void deleteQuizGroupAccessRestrictions(DeleteQuizGroupAccessRestrictionsRequest deleteQuizGroupAccessRestrictionsRequest) {
        quizGroupAccessRestrictionRepository.deleteByGroupIdAndStudentIdIn(
                deleteQuizGroupAccessRestrictionsRequest.groupId(),
                deleteQuizGroupAccessRestrictionsRequest.studentIds()
        );
    }

    private Set<Student> getEnrolledStudentsInCourse(Course course) {
        return course.getCourseStudents().stream()
                .map(CourseStudent::getStudent)
                .collect(Collectors.toSet());
    }

    private Set<Student> getStudentsByIds(Set<UUID> studentIds) {
        return studentIds.stream()
                .map(id -> userServiceHelper.findByIdAndTypeOrThrow(id, Student.class))
                .collect(Collectors.toSet());
    }
}
