package com.portfolio.lmsbackend.repository.user;

import com.portfolio.lmsbackend.dto.staff.admin.management.user.request.GetAdminUserSummaryRequest;
import com.portfolio.lmsbackend.enums.user.UserSortField;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.CriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AdminUserCriteriaRepository extends CriteriaRepository<User, GetAdminUserSummaryRequest> {
    protected AdminUserCriteriaRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    protected Predicate getPredicate(Root<User> root, GetAdminUserSummaryRequest searchRequest) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(searchRequest.search())) {
            addSearchPredicate(root, predicates, searchRequest.search());
        }

        if (Objects.nonNull(searchRequest.type())) {
            predicates.add(getCriteriaBuilder().equal(root.get("type"), searchRequest.type()));

            if (Objects.nonNull(searchRequest.staffRole()) && searchRequest.type() == UserType.STAFF) {
                predicates.add(getCriteriaBuilder().equal(
                        getCriteriaBuilder().treat(root, Staff.class).get("role"), searchRequest.staffRole()));
            }
        }

        if (Objects.nonNull(searchRequest.emailVerified())) {
            predicates.add(getCriteriaBuilder().equal(root.get("emailVerified"), searchRequest.emailVerified()));
        }

        if (Objects.nonNull(searchRequest.status())) {
            predicates.add(getCriteriaBuilder().equal(root.get("status"), searchRequest.status()));
        }

        return getCriteriaBuilder().and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected void setOrder(GetAdminUserSummaryRequest searchRequest, CriteriaQuery<User> criteriaQuery, Root<User> root) {
        List<Order> orders = new ArrayList<>();

        Expression<?> sortExp = searchRequest.sortField() == UserSortField.STAFF_ROLE ?
                getCriteriaBuilder().treat(root, Staff.class).get(searchRequest.sortField().getField()) :
                root.get(searchRequest.sortField().getField());

        orders.add(searchRequest.sortDirection().isAscending() ?
                getCriteriaBuilder().asc(sortExp) :
                getCriteriaBuilder().desc(sortExp));

        if (searchRequest.sortField() != UserSortField.CREATED_AT) {
            orders.add(getCriteriaBuilder().asc(root.get(UserSortField.CREATED_AT.getField())));
        }

        criteriaQuery.orderBy(orders);
    }

    private void addSearchPredicate(Root<User> root, List<Predicate> predicates, String search) {
        List<Predicate> searchPredicates = new ArrayList<>();

        Expression<String> idExp = getCriteriaBuilder().function("BIN_TO_UUID", String.class,
                root.get("id"));

        searchPredicates.add(getCriteriaBuilder().like(idExp, "%" + search + "%"));
        addSearchFullNamePredicate(root, searchPredicates, search);
        searchPredicates.add(getCriteriaBuilder().like(root.get("email"), "%" + search + "%"));

        predicates.add(getCriteriaBuilder().or(searchPredicates.toArray(new Predicate[0])));
    }

    private void addSearchFullNamePredicate(Root<User> root, List<Predicate> searchPredicates, String search) {
        List<Predicate> searchNamePredicates = new ArrayList<>();

        Expression<String> firstNameExp = root.get("firstName");
        Expression<String> lastNameExp = root.get("lastName");

        Set<Expression<String>> fullNameExps = new HashSet<>();
        fullNameExps.add(createFullNameExp(firstNameExp, lastNameExp));
        fullNameExps.add(createFullNameExp(lastNameExp, firstNameExp));

        for (Expression<String> fullName : fullNameExps) {
            searchNamePredicates.add(getCriteriaBuilder().like(fullName, "%" + search + "%"));
        }

        searchPredicates.add(getCriteriaBuilder().or(searchNamePredicates.toArray(new Predicate[0])));
    }

    private Expression<String> createFullNameExp(Expression<String> nameExp1, Expression<String> nameExp2) {
        String space = " ";
        return getCriteriaBuilder().concat(
                getCriteriaBuilder().concat(
                        nameExp1,
                        space
                ),
                nameExp2
        );
    }
}
