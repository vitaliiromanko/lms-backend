package com.portfolio.lmsbackend.controller.staff.admin;

import com.portfolio.lmsbackend.dto.staff.admin.management.category.request.CreateAdminCategoryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.request.UpdateAdminCategoryRequest;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.response.GetAdminCategoryResponse;
import com.portfolio.lmsbackend.dto.staff.admin.management.category.response.GetAdminCategorySummaryResponse;
import com.portfolio.lmsbackend.service.application.staff.admin.CategoryManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(
        name = "Administrator / CategoryManagementController",
        description = "Endpoints for managing categories"
)
@RestController
@RequestMapping("/category/manage")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CategoryManagementController {
    private final CategoryManagementService categoryManagementService;

    @Operation(
            summary = "Create category",
            description = "Endpoint to create a new category."
    )
    @PostMapping
    public ResponseEntity<String> create(
            @Valid @RequestBody CreateAdminCategoryRequest createAdminCategoryRequest
    ) {
        categoryManagementService.create(createAdminCategoryRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Get all categories information",
            description = "Endpoint to retrieve information about all existing categories."
    )
    @GetMapping
    public ResponseEntity<List<GetAdminCategorySummaryResponse>> getAll() {
        return ResponseEntity.ok().body(categoryManagementService.getAll());
    }

    @Operation(
            summary = "Get category information",
            description = "Endpoint to retrieve information about a category."
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<GetAdminCategoryResponse> getOne(
            @PathVariable UUID categoryId
    ) {
        return ResponseEntity.ok().body(categoryManagementService.getOne(categoryId));
    }

    @Operation(
            summary = "Update category",
            description = "Endpoint to update information about a category."
    )
    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateAdminCategoryRequest updateAdminCategoryRequest
    ) {
        categoryManagementService.update(updateAdminCategoryRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Delete category",
            description = "Endpoint to delete a category."
    )
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> delete(
            @PathVariable UUID categoryId
    ) {
        categoryManagementService.delete(categoryId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
