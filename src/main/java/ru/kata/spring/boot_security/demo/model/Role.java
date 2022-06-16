package ru.kata.spring.boot_security.demo.model;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "role_name")
    private String name;

    public Role(String name) {
        this.name = name;
    }
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    @Override
    public String toString() {
        return name.substring(5);
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}