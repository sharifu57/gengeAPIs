package com.genge.GengeAPIs.product.controller;

import com.genge.GengeAPIs.product.dto.CategoryAddRequest;
import com.genge.GengeAPIs.product.dto.CategoryUpdateRequest;
import com.genge.GengeAPIs.product.entity.Category;
import com.genge.GengeAPIs.product.service.CategoryService;
import com.genge.GengeAPIs.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public APIResponse<Category> create(
            @RequestBody CategoryAddRequest request
    ) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/update")
    public APIResponse<Category> update(
            @RequestBody CategoryUpdateRequest request
    ) {
        return categoryService.updateCategory(request);
    }

    @GetMapping("/fetch-all")
    public APIResponse<?> getAll() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public APIResponse<?> getById(
            @Param("id") String id
    ) {
        return categoryService.getCategoryById(Long.valueOf(id));
    }

    @DeleteMapping("/{id}")
    public APIResponse<?> delete(
            @Param("id") String id
    ) {
        return categoryService.deleteCategory(Long.valueOf(id));
    }

    @PutMapping("/{id}/activate")
    public APIResponse<?> activate(
            @PathVariable Long id
    ) {
        return categoryService.activateCategory(id);
    }

    @PutMapping("/{id}/deactivate")
    public APIResponse<?> deactivate(
            @PathVariable Long id
    ) {
        return categoryService.deactivateCategory(id);
    }
}
