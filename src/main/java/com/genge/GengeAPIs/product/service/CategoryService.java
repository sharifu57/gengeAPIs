package com.genge.GengeAPIs.product.service;

import com.genge.GengeAPIs.product.dto.CategoryAddRequest;
import com.genge.GengeAPIs.product.dto.CategoryUpdateRequest;
import com.genge.GengeAPIs.product.entity.Category;
import com.genge.GengeAPIs.product.repository.CategoryRepository;
import com.genge.GengeAPIs.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public APIResponse createCategory(CategoryAddRequest request){
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return APIResponse.fail("Category name is required");
        }

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            return APIResponse.fail("Category already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setImageUrl(request.getImageUrl());
        category.setActive(true);

        category = categoryRepository.save(category);

        return APIResponse.success(
                "Category created successfully",
                category
        );
    }

    public APIResponse<Category> updateCategory(CategoryUpdateRequest request) {

        Category category = categoryRepository
                .findById(request.getId())
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        category.setName(request.getName());
        category.setImageUrl(request.getImageUrl());

        category = categoryRepository.save(category);

        return APIResponse.success(
                "Category updated successfully",
                category
        );
    }

    public APIResponse<List<Category>> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return APIResponse.success(
                "Categories retrieved successfully",
                categories
        );
    }

    public APIResponse<Category> getCategoryById(Long id) {

        Category category = categoryRepository
                .findById(id)
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        return APIResponse.success(
                "Category retrieved successfully",
                category
        );
    }

    public APIResponse<String> deleteCategory(Long id) {

        Category category = categoryRepository
                .findById(id)
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        categoryRepository.delete(category);

        return APIResponse.success(
                "Category deleted successfully",
                "Deleted"
        );
    }

    public APIResponse<Category> activateCategory(Long id) {

        Category category = categoryRepository
                .findById(id)
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        category.setActive(true);

        category = categoryRepository.save(category);

        return APIResponse.success(
                "Category activated successfully",
                category
        );
    }

    public APIResponse<Category> deactivateCategory(Long id) {

        Category category = categoryRepository
                .findById(id)
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        category.setActive(false);

        category = categoryRepository.save(category);

        return APIResponse.success(
                "Category deactivated successfully",
                category
        );
    }
}
