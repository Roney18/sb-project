package com.roney.sb_project.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"roles", "cart", "products"}) // ✅ exclude collections & circular refs
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "UserRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @OneToOne(mappedBy = "user")
    private Cart cart;
}
