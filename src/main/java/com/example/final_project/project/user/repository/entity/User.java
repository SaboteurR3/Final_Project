package com.example.final_project.project.user.repository.entity;

import com.example.final_project.project.order.repository.entity.Order;
import com.example.final_project.project.auth.repository.entity.Role;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users")
    @SequenceGenerator(name = "users", sequenceName = "seq_users", allocationSize = 1, initialValue = 1000)
    @Column(name = "users_id")
    private Long id;
    @Column(name = "users_name")
    private String name;
    @Column(name = "users_email")
    private String email;
    @Column(name = "users_balance")
    private BigDecimal balance;
    @Column(name = "users_password")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable ( // users_roles table Many to Many
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "users_id", referencedColumnName = "users_id"),
            inverseJoinColumns = @JoinColumn (
                    name = "roles_id", referencedColumnName = "roles_id"
            )
    )
    private List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Order> orders;

    public User(String name, String email, String password, List<Role> roles, BigDecimal balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.balance = balance;
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
