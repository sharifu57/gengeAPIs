package com.genge.GengeAPIs.user.entity;

import com.genge.GengeAPIs.common.entity.Auditable;
import com.genge.GengeAPIs.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@Table(
        name = "auth_user"
)
@EntityListeners(EntityListeners.class)
public class User extends Auditable<String> implements Serializable {

    @Column(name = "fullName")
    private String fullName;

    @Column(unique = true, name = "phone")
    private String phone;

    @LastModifiedDate
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    private UserStatus status=UserStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (roles != null) {
            roles.forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r.getName()));
                if (r.getPermissions() != null) {
                    r.getPermissions().forEach(p ->
                            authorities.add(new SimpleGrantedAuthority(p.getName())));
                }
            });
        }
        return authorities;
    }
}
