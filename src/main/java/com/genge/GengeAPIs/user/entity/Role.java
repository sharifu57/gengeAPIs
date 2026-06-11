package com.genge.GengeAPIs.user.entity;

import com.genge.GengeAPIs.common.entity.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name="ROLE")
public class Role extends Auditable<String> implements Serializable {
    @Column(unique = true)
    private String name;
    private String description;
    private String status;
    private List<Permissions> permissions;
}
