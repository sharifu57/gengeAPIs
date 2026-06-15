package com.genge.GengeAPIs.product.entity;

import com.genge.GengeAPIs.common.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(EntityListeners.class)
public class Product extends Auditable<String> implements Serializable {
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private BigDecimal price;
    private BigDecimal discountedPrice;

    private Integer quantity;
    private String unit;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String sku;
    private Boolean isActive=true;
}
