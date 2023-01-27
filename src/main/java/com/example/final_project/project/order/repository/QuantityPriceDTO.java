package com.example.final_project.project.order.repository;

import java.math.BigDecimal;

public interface QuantityPriceDTO {
    BigDecimal getQuantity();
    BigDecimal getPrice();
    Long getId();
}
