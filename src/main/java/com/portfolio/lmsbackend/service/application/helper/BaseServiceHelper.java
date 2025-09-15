package com.portfolio.lmsbackend.service.application.helper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseServiceHelper<T, R extends JpaRepository<T, UUID>, E1 extends ResponseStatusException> {
    protected final R repository;
    private final Supplier<E1> exceptionSupplier1;

    public T findByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(exceptionSupplier1);
    }
}
