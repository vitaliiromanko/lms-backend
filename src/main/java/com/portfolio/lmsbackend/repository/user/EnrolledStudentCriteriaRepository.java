package com.portfolio.lmsbackend.repository.user;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.GetEnrolledStudentsByCourseRequest;
import com.portfolio.lmsbackend.enums.user.EnrolledStudentSortField;
import com.portfolio.lmsbackend.model.course.coursestudent.CourseStudent;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.repository.CriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.portfolio.lmsbackend.repository.CriteriaRepositoryHelper.addSearchPredicate;

@Repository
public class EnrolledStudentCriteriaRepository extends CriteriaRepository<CourseStudent, GetEnrolledStudentsByCourseRequest> {
    protected EnrolledStudentCriteriaRepository(EntityManager entityManager) {
        super(CourseStudent.class, entityManager);
    }

    @Override
    protected Predicate getPredicate(Root<CourseStudent> root, CriteriaQuery<?> criteriaQuery, GetEnrolledStudentsByCourseRequest searchRequest) {
        List<Predicate> predicates = new ArrayList<>();

        addEnrolledPredicate(root, predicates, searchRequest.courseId());

        if (Objects.nonNull(searchRequest.search())) {
            Join<CourseStudent, Student> joinStudent = getOrCreateJoinStudent(root);
            addSearchPredicate(joinStudent, getCriteriaBuilder(), predicates, searchRequest.search());
        }

        return getCriteriaBuilder().and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected void setOrder(GetEnrolledStudentsByCourseRequest searchRequest, CriteriaQuery<CourseStudent> criteriaQuery, Root<CourseStudent> root) {
        List<Order> orders = new ArrayList<>();

        Expression<?> sortExp;
        if (searchRequest.sortField() == EnrolledStudentSortField.ENROLLED_AT) {
            sortExp = root.get(searchRequest.sortField().getField());
        } else {
            Join<CourseStudent, Student> joinStudent = getOrCreateJoinStudent(root);
            sortExp = joinStudent.get(searchRequest.sortField().getField());
        }

        orders.add(searchRequest.sortDirection().isAscending()
                ? getCriteriaBuilder().asc(sortExp)
                : getCriteriaBuilder().desc(sortExp));

        if (searchRequest.sortField() != EnrolledStudentSortField.ENROLLED_AT) {
            orders.add(getCriteriaBuilder().asc(root.get(EnrolledStudentSortField.ENROLLED_AT.getField())));
        }

        criteriaQuery.orderBy(orders);
    }

    @SuppressWarnings("unchecked")
    private Join<CourseStudent, Student> getOrCreateJoinStudent(Root<CourseStudent> root) {
        for (Join<CourseStudent, ?> join : root.getJoins()) {
            if (join.getAttribute().getName().equals("student") && join.getJavaType().equals(Student.class)) {
                return (Join<CourseStudent, Student>) join;
            }
        }
        return root.join("student", JoinType.LEFT);
    }


    private void addEnrolledPredicate(Root<CourseStudent> root, List<Predicate> predicates, UUID courseId) {
        predicates.add(getCriteriaBuilder().equal(root.get("course").get("id"), courseId));
    }
}
