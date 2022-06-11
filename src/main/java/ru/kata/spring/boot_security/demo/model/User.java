package ru.kata.spring.boot_security.demo.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password", updatable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    public User() {
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false))
    private Set<Role> roles = new HashSet<Role>();

    public User(String username, String password, String email, int age, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }
    public String getRolesAsString() {
        boolean first = true;
        String s = "";
        for (Role role : getRoles()) {
            if (!first) {
                s += ", ";
            }
            s += role.toString();
            first = false;
        }
        return s;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}
