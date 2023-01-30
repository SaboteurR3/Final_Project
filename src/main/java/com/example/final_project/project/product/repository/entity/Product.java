package com.example.final_project.project.product.repository.entity;

import com.example.final_project.project.order.repository.entity.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collection;
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "products_id")
    private Long id;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "product_complexity")
    private String complexity;
    @Column(name = "product_price")
    private BigDecimal price;
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Collection<Order> orders;


    public Product(String product_name, String complexity, BigDecimal price) {
        this.product_name = product_name;
        this.complexity = complexity;
        this.price = price;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", complexity='" + complexity + '\'' +
                ", price=" + price +
                '}';
    }
}
