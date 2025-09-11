package com.portfolio.lmsbackend.repository;

import com.portfolio.lmsbackend.model.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CriteriaRepositoryHelper {
    public static void addSearchPredicate(From<?, ? extends User> from, CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicates, String search) {
        List<Predicate> searchPredicates = new ArrayList<>();

        Expression<String> idExp = criteriaBuilder.function("BIN_TO_UUID", String.class,
                from.get("id"));

        searchPredicates.add(criteriaBuilder.like(idExp, "%" + search + "%"));
        addSearchFullNamePredicate(from, criteriaBuilder, searchPredicates, search);
        searchPredicates.add(criteriaBuilder.like(from.get("email"), "%" + search + "%"));

        predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
    }

    public static void addSearchFullNamePredicate(From<?, ? extends User> from, CriteriaBuilder criteriaBuilder,
                                                  List<Predicate> searchPredicates, String search) {
        List<Predicate> searchNamePredicates = new ArrayList<>();

        Expression<String> firstNameExp = from.get("firstName");
        Expression<String> lastNameExp = from.get("lastName");

        Set<Expression<String>> fullNameExps = new HashSet<>();
        fullNameExps.add(createFullNameExp(criteriaBuilder, firstNameExp, lastNameExp));
        fullNameExps.add(createFullNameExp(criteriaBuilder, lastNameExp, firstNameExp));

        for (Expression<String> fullName : fullNameExps) {
            searchNamePredicates.add(criteriaBuilder.like(fullName, "%" + search + "%"));
        }

        searchPredicates.add(criteriaBuilder.or(searchNamePredicates.toArray(new Predicate[0])));
    }

    private static Expression<String> createFullNameExp(CriteriaBuilder criteriaBuilder,
                                                        Expression<String> nameExp1, Expression<String> nameExp2) {
        String space = " ";
        return criteriaBuilder.concat(
                criteriaBuilder.concat(
                        nameExp1,
                        space
                ),
                nameExp2
        );
    }
}
