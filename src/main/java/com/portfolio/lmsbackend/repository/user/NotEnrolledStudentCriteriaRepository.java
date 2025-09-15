package com.portfolio.lmsbackend.repository.user;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.GetNotEnrolledStudentsByCourseRequest;
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
public class NotEnrolledStudentCriteriaRepository extends CriteriaRepository<Student, GetNotEnrolledStudentsByCourseRequest> {
    protected NotEnrolledStudentCriteriaRepository(EntityManager entityManager) {
        super(Student.class, entityManager);
    }

    @Override
    protected Predicate getPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, GetNotEnrolledStudentsByCourseRequest searchRequest) {
        List<Predicate> predicates = new ArrayList<>();

        addNotEnrolledPredicate(root, criteriaQuery, predicates, searchRequest.courseId());

        if (Objects.nonNull(searchRequest.search())) {
            addSearchPredicate(root, getCriteriaBuilder(), predicates, searchRequest.search());
        }

        return getCriteriaBuilder().and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected void setOrder(GetNotEnrolledStudentsByCourseRequest searchRequest, CriteriaQuery<Student> criteriaQuery, Root<Student> root) {
        List<Order> orders = new ArrayList<>();

        Expression<?> sortExp = root.get(searchRequest.sortField().getField());

        orders.add(searchRequest.sortDirection().isAscending()
                ? getCriteriaBuilder().asc(sortExp)
                : getCriteriaBuilder().desc(sortExp));

        orders.add(getCriteriaBuilder().asc(root.get("createdAt")));

        criteriaQuery.orderBy(orders);
    }

    private void addNotEnrolledPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery,
                                         List<Predicate> predicates, UUID courseId) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<CourseStudent> courseStudent = subquery.from(CourseStudent.class);
        subquery.select(courseStudent.get("student").get("id"))
                .where(getCriteriaBuilder().equal(courseStudent.get("course").get("id"), courseId));

        predicates.add(getCriteriaBuilder().not(root.get("id").in(subquery)));
    }
}
