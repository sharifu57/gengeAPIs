package com.genge.GengeAPIs.user.entity;

import com.genge.GengeAPIs.common.entity.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "PERMISSION")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Permissions extends Auditable<String> implements Serializable {
    @Column(unique = true)
    private String name;

    private String description;
    private String status="A";
}
