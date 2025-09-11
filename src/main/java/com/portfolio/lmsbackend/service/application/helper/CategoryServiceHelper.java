package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.CategoryNotFoundException;
import com.portfolio.lmsbackend.model.course.Category;
import com.portfolio.lmsbackend.repository.course.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryServiceHelper extends BaseServiceHelper<Category, CategoryRepository, CategoryNotFoundException> {
    protected CategoryServiceHelper(CategoryRepository repository) {
        super(repository, CategoryNotFoundException::new);
    }
}
