package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.dto.staff.instructor.management.student.request.GetQuizGroupAccessRestrictionsRequest;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;
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
public class QuizGroupAccessRestrictionCriteriaRepository extends CriteriaRepository<QuizGroupAccessRestriction, GetQuizGroupAccessRestrictionsRequest> {
    protected QuizGroupAccessRestrictionCriteriaRepository(EntityManager entityManager) {
        super(QuizGroupAccessRestriction.class, entityManager);
    }

    @Override
    protected Predicate getPredicate(Root<QuizGroupAccessRestriction> root, CriteriaQuery<?> criteriaQuery, GetQuizGroupAccessRestrictionsRequest searchRequest) {
        List<Predicate> predicates = new ArrayList<>();

        addGroupPredicate(root, predicates, UUID.fromString(searchRequest.groupId()));

        if (Objects.nonNull(searchRequest.search())) {
            Join<QuizGroupAccessRestriction, Student> joinStudent = getOrCreateJoinStudent(root);
            addSearchPredicate(joinStudent, getCriteriaBuilder(), predicates, searchRequest.search());
        }

        if (Objects.nonNull(searchRequest.type())) {
            predicates.add(getCriteriaBuilder().equal(root.get("type"), searchRequest.type()));
        }

        return getCriteriaBuilder().and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected void setOrder(GetQuizGroupAccessRestrictionsRequest searchRequest, CriteriaQuery<QuizGroupAccessRestriction> criteriaQuery, Root<QuizGroupAccessRestriction> root) {
        criteriaQuery.orderBy(getCriteriaBuilder().asc(root.get("createdAt")));
    }

    @SuppressWarnings("unchecked")
    private Join<QuizGroupAccessRestriction, Student> getOrCreateJoinStudent(Root<QuizGroupAccessRestriction> root) {
        for (Join<QuizGroupAccessRestriction, ?> join : root.getJoins()) {
            if (join.getAttribute().getName().equals("student") && join.getJavaType().equals(Student.class)) {
                return (Join<QuizGroupAccessRestriction, Student>) join;
            }
        }
        return root.join("student", JoinType.LEFT);
    }

    private void addGroupPredicate(Root<QuizGroupAccessRestriction> root, List<Predicate> predicates, UUID groupId) {
        predicates.add(getCriteriaBuilder().equal(root.get("group").get("id"), groupId));
    }
}
