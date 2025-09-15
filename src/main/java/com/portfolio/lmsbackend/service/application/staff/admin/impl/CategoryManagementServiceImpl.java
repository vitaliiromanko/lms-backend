package com.portfolio.lmsbackend.service.application.staff.admin.impl;

import com.portfolio.lmsbackend.dto.staff.admin.management.category.request.CreateAdminCategoryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.request.UpdateAdminCategoryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.response.GetAdminCategoryResponse;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.response.GetAdminCategorySummaryResponse;
import com.portfolio.lmsbackend.exception.course.CategoryAlreadyExistsException;
import com.portfolio.lmsbackend.exception.course.CategoryHasCoursesException;
import com.portfolio.lmsbackend.model.course.Category;
import com.portfolio.lmsbackend.repository.course.CategoryRepository;
import com.portfolio.lmsbackend.service.application.helper.CategoryServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.admin.CategoryManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryManagementServiceImpl implements CategoryManagementService {
    private final CategoryRepository categoryRepository;
    private final CategoryServiceHelper categoryServiceHelper;

    @Override
    public void create(CreateAdminCategoryRequest createAdminCategoryRequest) {
        if (categoryRepository.existsByTitle(createAdminCategoryRequest.title())) {
            throw new CategoryAlreadyExistsException();
        }

        categoryRepository.save(new Category(createAdminCategoryRequest.title()));
    }

    @Override
    @Transactional
    public List<GetAdminCategorySummaryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(GetAdminCategorySummaryResponse::new)
                .sorted(Comparator.comparing(c -> c.title().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GetAdminCategoryResponse getOne(UUID categoryId) {
        return new GetAdminCategoryResponse(categoryServiceHelper.findByIdOrThrow(categoryId));
    }

    @Override
    public void update(UpdateAdminCategoryRequest updateAdminCategoryRequest) {
        Category category = categoryServiceHelper.findByIdOrThrow(updateAdminCategoryRequest.categoryId());

        if (!category.getTitle().equals(updateAdminCategoryRequest.title())) {
            if (categoryRepository.existsByTitle(updateAdminCategoryRequest.title())) {
                throw new CategoryAlreadyExistsException();
            }

            category.setTitle(updateAdminCategoryRequest.title());
        }

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(UUID categoryId) {
        Category category = categoryServiceHelper.findByIdOrThrow(categoryId);

        if (!category.getCourses().isEmpty()) {
            throw new CategoryHasCoursesException();
        }

        categoryRepository.delete(category);
    }
}
