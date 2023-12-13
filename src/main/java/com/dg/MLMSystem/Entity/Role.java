package com.dg.MLMSystem.Entity;

import com.dg.MLMSystem.DTO.UserRoleEnum;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

//    @Column(nullable = false)
//    private String name;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum name; // Enum for roles (e.g., ADMIN, USER)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoleEnum getName() {
        return name;
    }

    public void setName(UserRoleEnum name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}


