package com.example.final_project.project.order.repository.entity;

import com.example.final_project.project.product.repository.entity.Product;
import com.example.final_project.project.user.repository.entity.User;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "orders_id")
    private Long id;

    @Column(name = "orders_quantity")
    private int quantity;
    @Column(name = "orders_date")
    private String date;

    @Column(name = "order_status")
    private String status;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable ( // products_orders table Many to Many
            name = "products_orders",
            joinColumns = @JoinColumn(
                    name = "orders_id", referencedColumnName = "orders_id",
                    nullable = false, updatable = false
            ),
            inverseJoinColumns = @JoinColumn (
                    name = "products_id", referencedColumnName = "products_id",
                    nullable = false, updatable = false
            )
    )

    @Column(name = "product_id")
    private Collection<Product> products;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Order(int quantity, String date, String status, Collection<Product> products, User user) {
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.products = products;
        this.user = user;
    }

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public User getUser() {
        return user;
    }
}
