package com.genge.GengeAPIs.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductAddRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private Integer quantity;
    private String unit;
    private String imageUrl;
    private Long categoryId;
}
