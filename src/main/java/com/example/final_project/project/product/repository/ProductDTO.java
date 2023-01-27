package com.example.final_project.project.product.repository;

import java.math.BigDecimal;

public class ProductDTO {
    private String product_name;
    private BigDecimal product_price;

    public ProductDTO(String product_name, BigDecimal product_price) {
        this.product_name = product_name;
        this.product_price = product_price;
    }

    public String getName() {
        return product_name;
    }

    public BigDecimal getPrice() {
        return product_price;
    }
}
