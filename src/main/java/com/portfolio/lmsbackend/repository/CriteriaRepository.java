package com.portfolio.lmsbackend.repository;

import com.portfolio.lmsbackend.dto.SearchRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Getter(AccessLevel.PROTECTED)
public abstract class CriteriaRepository<T, S extends SearchRequest> {
    private final Class<T> entityClass;
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    protected CriteriaRepository(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<T> findByCriteria(S searchRequest) {
        CriteriaQuery<T> criteriaQuery = getCriteriaBuilder().createQuery(getEntityClass());
        Root<T> root = criteriaQuery.from(getEntityClass());

        Predicate predicate = getPredicate(root, criteriaQuery, searchRequest);
        criteriaQuery.where(predicate);
        setOrder(searchRequest, criteriaQuery, root);

        TypedQuery<T> typedQuery = getTypedQuery(criteriaQuery, searchRequest);
        Pageable pageable = PageRequest.of(searchRequest.pageNumber(), searchRequest.pageSize());
        long count = getCount(searchRequest);

        return new PageImpl<>(typedQuery.getResultList(), pageable, count);
    }

    protected abstract Predicate getPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, S searchRequest);

    protected abstract void setOrder(S searchRequest, CriteriaQuery<T> criteriaQuery, Root<T> root);

    protected TypedQuery<T> getTypedQuery(CriteriaQuery<T> criteriaQuery, S searchRequest) {
        TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaQuery);
        typedQuery.setFirstResult(searchRequest.pageNumber() * searchRequest.pageSize());
        typedQuery.setMaxResults(searchRequest.pageSize());
        return typedQuery;
    }

    protected long getCount(S searchRequest) {
        CriteriaQuery<Long> countQuery = getCriteriaBuilder().createQuery(Long.class);
        Root<T> countRoot = countQuery.from(getEntityClass());
        Predicate predicate = getPredicate(countRoot, countQuery, searchRequest);
        countQuery.select(getCriteriaBuilder().count(countRoot)).where(predicate);
        return getEntityManager().createQuery(countQuery).getSingleResult();
    }
}
