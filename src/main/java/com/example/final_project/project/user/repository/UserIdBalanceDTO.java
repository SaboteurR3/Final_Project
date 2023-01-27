package com.example.final_project.project.user.repository;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public class UserIdBalanceDTO {
    private Long id;
    private BigDecimal balance;
    private String email;

    public UserIdBalanceDTO(Long id, BigDecimal balance, String email) {
        this.id = id;
        this.balance = balance;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }
}
