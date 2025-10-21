package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.dto.staff.instructor.management.attempt.request.GetFinishedAttemptsByQuizGroupRequest;
import com.portfolio.lmsbackend.enums.content.quiz.AttemptStatus;
import com.portfolio.lmsbackend.enums.content.quiz.FinishedAttemptSortField;
import com.portfolio.lmsbackend.enums.content.quiz.FinishedAttemptStatus;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.CriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.portfolio.lmsbackend.repository.CriteriaRepositoryHelper.addUserSearchPredicate;

@Repository
public class AttemptCriteriaRepository extends CriteriaRepository<Attempt, GetFinishedAttemptsByQuizGroupRequest> {
    protected AttemptCriteriaRepository(EntityManager entityManager) {
        super(Attempt.class, entityManager);
    }

    @Override
    protected Predicate getPredicate(Root<Attempt> root, CriteriaQuery<?> criteriaQuery,
                                     GetFinishedAttemptsByQuizGroupRequest searchRequest) {
        List<Predicate> predicates = new ArrayList<>();

        addGroupPredicate(root, predicates, searchRequest.quizGroupId());

        if (Objects.nonNull(searchRequest.search())) {
            addSearchPredicate(root, getCriteriaBuilder(), predicates, searchRequest.search());
        }

        if (Objects.nonNull(searchRequest.status())) {
            switch (searchRequest.status()) {
                case FinishedAttemptStatus.PENDING_GRADING ->
                        predicates.add(getCriteriaBuilder().equal(root.get("status"), AttemptStatus.PENDING_GRADING));
                case FinishedAttemptStatus.GRADED ->
                        predicates.add(getCriteriaBuilder().equal(root.get("status"), AttemptStatus.GRADED));
            }
        } else {
            predicates.add(getCriteriaBuilder().notEqual(root.get("status"), AttemptStatus.STARTED));
        }

        if (Objects.nonNull(searchRequest.performerType())) {
            predicates.add(getCriteriaBuilder().equal(getOrCreateJoinUser(root).get("type"),
                    searchRequest.performerType()));
        }

        return getCriteriaBuilder().and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected void setOrder(GetFinishedAttemptsByQuizGroupRequest searchRequest, CriteriaQuery<Attempt> criteriaQuery,
                            Root<Attempt> root) {
        List<Order> orders = new ArrayList<>();

        Expression<?> sortExp = switch (searchRequest.sortField()) {
            case FinishedAttemptSortField.STATUS,
                 FinishedAttemptSortField.STARTED_AT,
                 FinishedAttemptSortField.FINISHED_AT -> root.get(searchRequest.sortField().getField());
            case FinishedAttemptSortField.FIRST_NAME,
                 FinishedAttemptSortField.LAST_NAME,
                 FinishedAttemptSortField.EMAIL -> getOrCreateJoinUser(root).get(searchRequest.sortField().getField());
        };

        orders.add(searchRequest.sortDirection().isAscending()
                ? getCriteriaBuilder().asc(sortExp)
                : getCriteriaBuilder().desc(sortExp));

        if (searchRequest.sortField() != FinishedAttemptSortField.FINISHED_AT) {
            orders.add(getCriteriaBuilder().desc(root.get(FinishedAttemptSortField.FINISHED_AT.getField())));
        }

        criteriaQuery.orderBy(orders);
    }

    private void addGroupPredicate(Root<Attempt> root, List<Predicate> predicates, UUID groupId) {
        predicates.add(getCriteriaBuilder().equal(root.get("quiz").get("group").get("id"), groupId));
    }

    private void addSearchPredicate(Root<Attempt> root, CriteriaBuilder criteriaBuilder,
                                    List<Predicate> predicates, String search) {
        List<Predicate> searchPredicates = new ArrayList<>();

        addAttemptSearchPredicate(root, criteriaBuilder, searchPredicates, search);
        addUserSearchPredicate(getOrCreateJoinUser(root), criteriaBuilder, searchPredicates, search);

        predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
    }

    private void addAttemptSearchPredicate(Root<Attempt> root, CriteriaBuilder criteriaBuilder,
                                           List<Predicate> predicates, String search) {
        List<Predicate> searchPredicates = new ArrayList<>();

        Expression<String> idExp = criteriaBuilder.function("BIN_TO_UUID", String.class,
                root.get("id"));

        searchPredicates.add(criteriaBuilder.like(idExp, "%" + search + "%"));

        predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
    }

    @SuppressWarnings("unchecked")
    private Join<Attempt, User> getOrCreateJoinUser(Root<Attempt> root) {
        for (Join<Attempt, ?> join : root.getJoins()) {
            if (join.getAttribute().getName().equals("user") && join.getJavaType().equals(User.class)) {
                return (Join<Attempt, User>) join;
            }
        }
        return root.join("user", JoinType.LEFT);
    }
}
