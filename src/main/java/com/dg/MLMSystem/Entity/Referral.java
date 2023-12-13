package com.dg.MLMSystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "referral")
public class Referral implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private User referrer; // The user who referred someone

    @ManyToOne
    @JsonBackReference
    private User referred; // The user who was referred

    private int level; // Referral level

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReferrer() {
        return referrer;
    }

    public void setReferrer(User referrer) {
        this.referrer = referrer;
    }

    public User getReferred() {
        return referred;
    }

    public void setReferred(User referred) {
        this.referred = referred;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Referral{" +
                "id=" + id +
                ", referrer=" + referrer +
                ", referred=" + referred +
                ", level=" + level +
                '}';
    }
}
