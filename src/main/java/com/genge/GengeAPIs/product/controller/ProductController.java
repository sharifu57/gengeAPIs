package com.genge.GengeAPIs.product.controller;

import com.genge.GengeAPIs.product.dto.ProductAddRequest;
import com.genge.GengeAPIs.product.dto.ProductUpdateRequest;
import com.genge.GengeAPIs.product.entity.Product;
import com.genge.GengeAPIs.product.service.ProductService;
import com.genge.GengeAPIs.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public APIResponse<?> create(
            @RequestBody ProductAddRequest request
    ) {
        return productService.createProduct(request);
    }

    @PutMapping("/update")
    public APIResponse<?> update(
            @RequestBody ProductUpdateRequest request
    ) {
        return productService.updateProduct(request);
    }

    @GetMapping("/fetch-all")
    public APIResponse<?> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public APIResponse<?> getById(
            @Param("id") String id
    ) {
        return productService.getProductById(Long.valueOf(id));
    }

    @DeleteMapping("/delete")
    public APIResponse<?> delete(
            @Param("id") String id
    ) {
        return productService.deleteProduct(Long.valueOf(id));
    }

    @PutMapping("/activate")
    public APIResponse<?> activate(
            @Param("id") String id
    ) {
        return productService.activateProduct(Long.valueOf(id));
    }

    @PutMapping("/deactivate")
    public APIResponse<?> deactivate(
            @Param("id") Long id
    ) {
        return productService.deactivateProduct(id);
    }

    @GetMapping("/products/category")
    public ResponseEntity<APIResponse<List<Product>>> getProductsByCategory(
            @Param("categoryId") String categoryId
    ) {
        System.out.println("======CATEGORYiiii::" + categoryId);
        return ResponseEntity.ok(
                productService.getProductsByCategory(categoryId)
        );
    }
}
