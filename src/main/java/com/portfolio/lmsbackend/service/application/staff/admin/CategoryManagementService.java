package com.portfolio.lmsbackend.service.application.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.category.request.CreateAdminCategoryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.request.UpdateAdminCategoryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.response.GetAdminCategoryResponse;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.response.GetAdminCategorySummaryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryManagementService {
    void create(CreateAdminCategoryRequest createAdminCategoryRequest);

    List<GetAdminCategorySummaryResponse> getAll();

    GetAdminCategoryResponse getOne(UUID categoryId);

    void update(UpdateAdminCategoryRequest updateAdminCategoryRequest);

    void delete(UUID categoryId);
}
