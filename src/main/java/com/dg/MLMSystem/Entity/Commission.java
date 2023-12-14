package com.dg.MLMSystem.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private UserInfo userInfo;
    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return userInfo;
    }

    public void setUser(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
