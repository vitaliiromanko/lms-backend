package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.course.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByTitle(String title);
}
