package com.genge.GengeAPIs.product.repository;

import com.genge.GengeAPIs.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Category> findByRowId(String rowId);
}
