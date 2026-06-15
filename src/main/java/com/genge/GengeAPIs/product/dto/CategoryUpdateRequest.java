package com.genge.GengeAPIs.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {
    private Long id;
    private String name;
    private String imageUrl;
}
