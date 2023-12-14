package com.dg.MLMSystem.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "referral")
public class Referral implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserInfo referrer; // The user who referred someone

    @ManyToOne
    private UserInfo referred; // The user who was referred

    private int level; // Referral level

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfo getReferrer() {
        return referrer;
    }

    public void setReferrer(UserInfo referrer) {
        this.referrer = referrer;
    }

    public UserInfo getReferred() {
        return referred;
    }

    public void setReferred(UserInfo referred) {
        this.referred = referred;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
