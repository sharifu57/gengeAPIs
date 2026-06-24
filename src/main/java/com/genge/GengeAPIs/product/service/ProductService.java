package com.genge.GengeAPIs.product.service;


import com.genge.GengeAPIs.product.dto.ProductAddRequest;
import com.genge.GengeAPIs.product.dto.ProductUpdateRequest;
import com.genge.GengeAPIs.product.entity.Category;
import com.genge.GengeAPIs.product.entity.Product;
import com.genge.GengeAPIs.product.repository.CategoryRepository;
import com.genge.GengeAPIs.product.repository.ProductRepository;
import com.genge.GengeAPIs.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public APIResponse<Product> createProduct(ProductAddRequest request) {

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setQuantity(request.getQuantity());
        product.setUnit(request.getUnit());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);
        product.setIsActive(true);

        product = productRepository.save(product);

        return APIResponse.success(
                "Product created successfully",
                product
        );
    }

    public APIResponse<Product> updateProduct(ProductUpdateRequest request) {

        Product product = productRepository
                .findById(request.getId())
                .orElse(null);

        if (product == null) {
            return APIResponse.fail("Product not found");
        }

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setQuantity(request.getQuantity());
        product.setUnit(request.getUnit());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        product = productRepository.save(product);

        return APIResponse.success(
                "Product updated successfully",
                product
        );
    }

    public APIResponse<List<Product>> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return APIResponse.success(
                "Products retrieved successfully",
                products
        );
    }

    public APIResponse<Product> getProductById(Long id) {

        Product product = productRepository
                .findById(id)
                .orElse(null);

        if (product == null) {
            return APIResponse.fail("Product not found");
        }

        return APIResponse.success(
                "Product retrieved successfully",
                product
        );
    }

    public APIResponse<String> deleteProduct(Long id) {

        Product product = productRepository
                .findById(id)
                .orElse(null);

        if (product == null) {
            return APIResponse.fail("Product not found");
        }

        productRepository.delete(product);

        return APIResponse.success(
                "Product deleted successfully",
                "Deleted"
        );
    }

    public APIResponse<Product> activateProduct(Long id) {

        Product product = productRepository
                .findById(id)
                .orElse(null);

        if (product == null) {
            return APIResponse.fail("Product not found");
        }

        product.setIsActive(true);

        product = productRepository.save(product);

        return APIResponse.success(
                "Product activated successfully",
                product
        );
    }

    public APIResponse<Product> deactivateProduct(Long id) {

        Product product = productRepository
                .findById(id)
                .orElse(null);

        if (product == null) {
            return APIResponse.fail("Product not found");
        }

        product.setIsActive(false);

        product = productRepository.save(product);

        return APIResponse.success(
                "Product deactivated successfully",
                product
        );
    }

    public APIResponse<List<Product>> getProductsByCategory(String categoryId) {

        Category category = categoryRepository
                .findByRowId(categoryId)
                .orElse(null);

        if (category == null) {
            return APIResponse.fail("Category not found");
        }

        List<Product> products = productRepository
                .findByCategoryIdAndIsActiveTrue(Long.valueOf(category.getId()));

        return APIResponse.success(
                "Products retrieved successfully",
                products
        );
    }
}
