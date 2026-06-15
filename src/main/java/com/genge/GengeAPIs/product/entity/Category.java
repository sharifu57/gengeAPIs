package com.genge.GengeAPIs.product.entity;

import com.genge.GengeAPIs.common.entity.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "categories")
@EntityListeners(EntityListeners.class)
public class Category extends Auditable<String> implements Serializable {
    private String name;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private boolean isActive=true;
}
